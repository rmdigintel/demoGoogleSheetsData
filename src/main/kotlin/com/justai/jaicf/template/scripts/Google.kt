package com.justai.jaicf.template.scripts

import com.justai.jaicf.template.myCollection
import com.londogard.nlp.stemmer.Stemmer
import com.londogard.nlp.utils.LanguageSupport
import com.mongodb.client.model.Filters
import org.bson.Document

data class TagPattern(
    val name: String,
    val pattern: String
)

val stemmer = Stemmer(LanguageSupport.ru)


fun makePattern(tag: String): String {
    return tag.split(" ").joinToString(
        prefix = "",
        separator = " ",
        postfix = "",
        limit = 3,
        truncated = "...",
        transform = { stemmer.stem(it.trim()) + "*" })
}

// returns list of pairs of new tags (tag, pattern)
// writes them to Global vars
fun parseTags(url: String): MutableList<TagPattern> {
    val tagsWithPats = mutableListOf<TagPattern>()
    println("----------")
    val response = getRequest(url)
    if (response == "400") {
        return tagsWithPats
    }
    // couldn't parse with gson, the data is an array
    val responseParsed = response.split("],[")
    for (each in responseParsed) {
        val eachParsed = (each.split("\",\""))
        val city = eachParsed[0].replace("\"", "").replace("[", "").replace("]", "")
        val tags = eachParsed[1].replace("\"", "").replace("[", "").replace("]", "").split(", ")
        for (tagRaw in tags) {
            val tag = tagRaw.trim().toLowerCase()
            val filter = Filters.eq("tag", tag)
            val foundTagWithCities: Document? = myCollection.find(filter).first()
            if (foundTagWithCities == null) {
                //add field in mongo
                val newDocument = createDocument(tag, arrayListOf<String>(city))
                myCollection.insertOne(newDocument)
                tagsWithPats += TagPattern(tag, makePattern(tag))
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
    return (tagsWithPats)
}