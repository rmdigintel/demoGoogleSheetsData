package com.justai.jaicf.template.scripts

import com.justai.jaicf.template.myCollection
import com.mongodb.client.model.Filters
import org.bson.Document

data class TagPattern(
    val name: String,
    val pattern: String
)


fun makePattern(token_raw: String): String {
    val token = token_raw.trim()
    if (token == " ") {
        return ""
    }
    // if token is long: add '*' for 1 or 2 last symbols (if they are vowels, й or ь)
    if (token.length > 3) {
        if (token.substring(token.length - 1, token.length) in VOWELS) {
            return if (token.substring(token.length-2, token.length - 1) in VOWELS) {
                val stem = token.substring(0, token.length - 2)
                "$stem*"
            } else {
                val stem = token.substring(0, token.length - 1)
                "$stem*"
            }
        } else {
            return if (token.substring(token.length - 1, token.length) == "ь" || token.substring(token.length - 1, token.length) == "й") {
                val stem = token.substring(0, token.length - 1)
                "$stem*"
            } else {
                "$token*"
            }
        }
    } else {
        return if (token in PREPOSITIONS) {
            token
        } else if (token.substring(token.length - 1, token.length) == "ь" || (token.length == 3 && token.substring(token.length - 1, token.length) in VOWELS)) {
            val stem = token.substring(0, token.length - 1)
            "$stem*"
        } else {
            "$token*"
        }
    }
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
                //make pattern for tag
                val tagSplitter = tag.split(" ")
                var wholePattern = "("
                if (tagSplitter.size == 1) {
                    wholePattern += makePattern(tag)
                } else {
                    for (i in (0..(tagSplitter.size - 1))) {
                        wholePattern += makePattern(tagSplitter[i])
                        if (i != (tagSplitter.size - 1)) {
                            wholePattern += " "
                        }
                    }
                }
                wholePattern += ")"
                tagsWithPats += TagPattern(tag, wholePattern)
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


// returns list of pairs of new tags (tag, pattern)
// writes them to Global vars
/*fun parseTags(url: String): MutableList<TagPattern> {
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
        val tagsLowerCase = mutableListOf<String>()
        for (tag_raw in tags) {
            val tag = tag_raw.trim().toLowerCase()
            if (!TAGS_AND_THEIR_CITIES.containsKey(tag)) {
                TAGS_AND_THEIR_CITIES[tag] = mutableListOf<String>(city)
                tagsLowerCase.add(tag)
            } else if (!TAGS_AND_THEIR_CITIES[tag]?.contains(city)!!) {
                TAGS_AND_THEIR_CITIES[tag]?.add(city)
                tagsLowerCase.add(tag)
            }
            if (tag !in ALL_TAGS) {
                ALL_TAGS.add(tag)
                val tagSplitter = tag.split(" ")
                var wholePattern = "("
                if (tagSplitter.size == 1) {
                    wholePattern += makePattern(tag)
                } else {
                    for (i in (0..(tagSplitter.size - 1))) {
                        wholePattern += makePattern(tagSplitter[i])
                        if (i != (tagSplitter.size - 1)) {
                            wholePattern += " "
                        }
                    }
                }
                wholePattern += ")"
                ALL_TAGS_PATTERNS.add(wholePattern)
                tagsWithPats += TagPattern(tag, wholePattern)
            }
            if (!MY_DATA.containsKey(city) || MY_DATA[city] != tagsLowerCase) {
                MY_DATA[city] = tagsLowerCase
            }
        }
    }
    return(tagsWithPats)
}*/

/*if (!TAGS_AND_THEIR_CITIES.containsKey(tag)) {
                TAGS_AND_THEIR_CITIES[tag] = mutableListOf<String>(city)
                tagsLowerCase.add(tag)
            } else if (!TAGS_AND_THEIR_CITIES[tag]?.contains(city)!!) {
                TAGS_AND_THEIR_CITIES[tag]?.add(city)
                tagsLowerCase.add(tag)
            }
            if (tag !in ALL_TAGS) {
                ALL_TAGS.add(tag)
                val tagSplitter = tag.split(" ")
                var wholePattern = "("
                if (tagSplitter.size == 1) {
                    wholePattern += makePattern(tag)
                } else {
                    for (i in (0..(tagSplitter.size - 1))) {
                        wholePattern += makePattern(tagSplitter[i])
                        if (i != (tagSplitter.size - 1)) {
                            wholePattern += " "
                        }
                    }
                }
                wholePattern += ")"
                ALL_TAGS_PATTERNS.add(wholePattern)
                tagsWithPats += TagPattern(tag, wholePattern)
            }*/
/*if (!MY_DATA.containsKey(city) || MY_DATA[city] != tagsLowerCase) {
    MY_DATA[city] = tagsLowerCase
}*/