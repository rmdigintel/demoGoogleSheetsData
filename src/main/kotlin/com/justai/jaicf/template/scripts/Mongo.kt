package com.justai.jaicf.template.scripts

import com.google.gson.Gson
import com.justai.jaicf.template.myCollection
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import org.bson.Document

data class MongoDocument(var tag: String, var destination: MutableList<String>)

fun createDocument(tag: String, destination: MutableList<String>): Document {
    val document: String? = Gson().toJson(MongoDocument(tag, destination))
    return Document.parse(document)
}

fun findFirstDocument(filterValue: String?, collection: MongoCollection<Document>): Document? {
    val filter = Filters.eq("tag", filterValue)
    return collection.find(filter).first()
}