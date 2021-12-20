package com.justai.jaicf.template.scripts

import com.google.gson.Gson
import org.bson.Document

data class MongoDocument(var tag: String, var destination: MutableList<String>)

fun createDocument(tag: String, destination: MutableList<String>): Document {
    val document: String? = Gson().toJson(MongoDocument(tag, destination))
    return Document.parse(document)
}

//val filter = Filters.eq("_id", "61bb407525eaba262fc5377b")
/*val filter = Filters.eq("tag", "море")
val myCollection = client.getDatabase("jaicf").getCollection("googleSheets").find(filter)
//val myCollection = client.getDatabase("jaicf").getCollection("googleSheets").find({"_id":"61bb407525eaba262fc5377b"})
var citiesWithTags = Document()
var tag = ""
//var citiesList = arrayListOf<String>()
var destination = arrayListOf<String>()
myCollection.forEach {

    println(it.toString())

    tag = it.getValue("tag") as String
    println(tag)
    destination = it.getValue("destination") as ArrayList<String>
    println(destination)
    println()
    //citiesWithTags = it.getValue("tags") as Document
    //tags = it.getValue("tags") as String
    //citiesList = it.getValue("Cities") as ArrayList<String>
}
//client.getDatabase("jaicf").getCollection("googleSheets").insertOne()
//println(myCollection)
//println(tags)
//println(citiesWithTags)*/

/*val myCollection = client.getDatabase("jaicf").getCollection("googleSheets")
    val destination = arrayListOf("Астрахань", "Ростов")
    val newDocument = createDocument("рыбалка", destination)
    myCollection.insertOne(newDocument)*/