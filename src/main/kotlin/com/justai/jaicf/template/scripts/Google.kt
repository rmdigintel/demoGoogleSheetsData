package com.justai.jaicf.template.scripts

import com.justai.jaicf.template.myCollection
import com.londogard.nlp.stemmer.Stemmer
import com.londogard.nlp.utils.LanguageSupport
import com.mongodb.client.model.Filters

data class TagPattern(
    val name: String,
    val pattern: String
)

val stemmer = Stemmer(LanguageSupport.ru)
typealias CityWithTags = Pair<String, List<String>>

fun parseResponseFromGoogle(line: String): CityWithTags{
    val lineSplit = line.split("\",\"")
    val city = lineSplit[0].replace("\"", "").replace("[", "").replace("]", "")
    val tags = lineSplit[1].replace("\"", "").replace("[", "").replace("]", "").split(", ")
    return CityWithTags(city, tags)
}

fun makePattern(tag: String): String {
    return tag.split(" ").joinToString(
        prefix = "",
        separator = " ",
        postfix = "",
        limit = 3,
        truncated = "...",
        transform = { stemmer.stem(it.trim()) + "*" })
}

// returns list of pairs of all tags (tag, pattern)
// writes them to Global vars
fun parseTags(url: String): List<TagPattern> {
    val allTags = mutableListOf<TagPattern>()
    println("----------")
    val response = getRequest(url)
    if (response == "400") {
        return allTags
    }
    // couldn't parse with gson, the data is an array
    val responseParsed = response?.split("],[")
    if (responseParsed != null) {
        for (each in responseParsed) {
            val (city, tags) = parseResponseFromGoogle(each)
            for (tagRaw in tags) {
                val tag = tagRaw.trim().toLowerCase()
                val foundTagWithCities = findFirstDocument(tag, myCollection)
                allTags += TagPattern(tag, makePattern(tag))
                if (foundTagWithCities == null) {
                    //add field in mongo
                    val newDocument = createDocument(tag, arrayListOf<String>(city))
                    myCollection.insertOne(newDocument)
                } else {
                    val destination = foundTagWithCities.getValue("destination") as MutableList<String>
                    if (!destination.contains(city)) {
                        destination.add(city)
                        val id = foundTagWithCities.getValue("_id")
                        myCollection.replaceOne(Filters.eq("_id", id), createDocument(tag, destination))
                    }
                }
            }
        }
    }
    return allTags.distinct()
}

fun updateTagsAndDestination(tags: List<TagPattern>) {
    var entityExists = false
    var needToImport = false
    // check if there are any tags in Google Sheet
    if (tags.isNotEmpty()) {
        println(tags)
        val project = exportProject()
        // check if request is ok
        if (project != null) {
            println(project)
            val tagsRecordsValues: MutableList<String> = mutableListOf()
            for (i in 0 until project.entities.size) {
                if (project.entities[i].entity.name == ENTITY_NAME) {
                    entityExists = true
                    for (each in project.entities[i].records) {
                        tagsRecordsValues += each.value
                    }
                    for (tag in tags){
                        // check if there is no record for a tag in entity "tags"
                        if (tag.name !in tagsRecordsValues) {
                            needToImport = true
                            val record = createRecord(listOf(tag.pattern), tag.name)
                            project.entities[i].records.add(record)
                            println(record)
                        }
                    }
                    break
                }
            }
            if (!entityExists){
                needToImport = true
                val newEntity = Entity(0, ENTITY_NAME)
                val records: MutableList<EntityPattern> = mutableListOf()
                for (tag in tags){
                    // check if there is no record for a tag in entity "tags"
                    val record = createRecord(listOf(tag.pattern), tag.name)
                    records.add(record)
                }
                project.entities.add(EntityWithRecords(newEntity, records))
            }
            if (needToImport){
                println("need")
                importProject(project)
            }
        }
    }
}