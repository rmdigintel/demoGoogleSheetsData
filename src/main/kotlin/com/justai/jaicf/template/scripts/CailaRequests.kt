package com.justai.jaicf.template.scripts

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.net.URL


private val client = OkHttpClient()

data class Entity(
    var id: Int,
    var name: String,
    var enabled: Boolean = true,
    var type: String = "dictionary",
    var priority: Int = 0,
    var noSpelling: Boolean = false,
    var noMorph: Boolean = false,
    var phoneticSearch: Boolean = false,
    var fuzzySearch: Boolean = false,
    var client: Boolean = false)

data class EntityPattern(
    var id: Int,
    var type: String, // type = "pattern" || "synonyms"
    var rule: List<String>,
    var value: String,
    var clientId: Any? = null)

data class Paging(
    val pageNum: Int,
    val pageSize: Int,
    val totalCount: Int)

data class EntityWithRecords(var entity: Entity, var records: MutableList<EntityPattern>)
data class EntityRecord(var data: EntityPattern, var clientId: Any? = null)


data class ProjectInfo(var id: String, var name: String, var folder: String)
data class STSSettings(val exactMatch: Any? = 1,
                       val lemmaMatch: Any? = 0.95,
                       val jaccardMatch: Any? = 0.5,
                       val jaccardMatchThreshold: Any? = 0.82,
                       val acronymMatch: Any? = 1,
                       val synonymMatch: Any? = 0,
                       val synonymContextWeight: Any? = 0,
                       val patternMatch: Any? = 1,
                       val throughPatternMatch: Any? = 0,
                       val wordSequence1: Any? = 0.8,
                       val wordSequence2:Any? = 0.9,
                       val wordSequence3: Any? = 1,
                       val intermediateAlternativesLimit: Any? = 5,
                       val finalAlternativesLimit: Any? = 5,
                       val idfShift: Any? = 0,
                       val idfMultiplier: Any? = 1,
                       val namedEntitiesRequired: Boolean = true)

data class ExtendedSettings(val patternsEnabled: Boolean = true,
                            val tokenizerEngine: String = "udpipe",
                            val stsSettings: STSSettings,
                            val cnnSettings: Any?,
                            val classicMLSettings: Any?,
                            val logregSettings: Any?,
                            val dictionaryAutogeneration: Any?,
                            val luceneAnalyzer: Any?,
                            val shareIntents: Any?,
                            val shareEntities: Any?,
                            val disableHieroglyphicsTokenization: Any?,
                            val allowedPatterns: List<Any?>,
                            val useShared: Any?,
                            val zflPatternsEnabled: Any?,
                            val externalNluSettings: Any?)

data class ProjectSettings(val language: String = "ru",
                           val spellingCorrection: Boolean = false,
                           val classificationAlgorithm: String = "sts",
                           val timezone: String = "UTC",
                           val extendedSettings: ExtendedSettings,
                           val shared: Boolean = false)

data class Project(
    var project: ProjectInfo,
    var settings: ProjectSettings,
    var intents: List<Any?>,
    var entities: MutableList<EntityWithRecords>,
    var enabledSystemEntities: List<String>,
    var spellerDictionary: List<Any>,
    var systemEntities: List<Any>
)

fun exportProject(): Project? {
    val body = RequestBody.create(null, byteArrayOf())
    val request = Request.Builder()
        .url("https://app.jaicp.com/cailapub/api/caila/p/b5bdcb48-76de-4c01-9f70-1408244c79b9/export")
        .post(body)
        .build()
    val response = client.newCall(request).execute()
    val responseParsed = response.body!!.string()
    val responseString = Gson().fromJson(responseParsed, Project::class.java)
   // println(responseString)
    return responseString
}

fun importProject(project: Project) {
    val json: String = Gson().toJson(project)
    val body = RequestBody.create(("application/json; charset=utf-8".toMediaTypeOrNull()), json)
    val request = Request.Builder()
        .url("https://app.jaicp.com/cailapub/api/caila/p/b5bdcb48-76de-4c01-9f70-1408244c79b9/import")
        .post(body)
        .build()
    val response = client.newCall(request).execute()
    val responseParsed = response.body!!.string()
    // println(responseString)
    println(Gson().fromJson(responseParsed, Project::class.java))
}

/*
fun getNamedEntities() {
    val request = Request.Builder()
        .url("https://app.jaicp.com/cailapub/api/caila/p/b5bdcb48-76de-4c01-9f70-1408244c79b9/entities")
        .get()
        .build()
    val response = client.newCall(request).execute()
    val responseParsed = response.body!!.string()
}*/

/*
fun postNewEntity(){
    val request = Request.Builder()
        .url("https://app.jaicp.com/cailapub/api/caila/p/b5bdcb48-76de-4c01-9f70-1408244c79b9/entities")
        .post()
        .build()
    val response = client.newCall(request).execute()
    val responseParsed = response.body!!.string()
}*/

/*
fun createNamedEntity(): Entity? {
    //var body = Entity("check")
    val entityRequest = Entity(
        id = 0,
        name = "check")
    val json: String = Gson().toJson(entityRequest)
    println(json)
    val body = RequestBody.create(("application/json; charset=utf-8".toMediaTypeOrNull()), json)
    val request = Request.Builder()
        .url("https://app.jaicp.com/cailapub/api/caila/p/b5bdcb48-76de-4c01-9f70-1408244c79b9/entities")
        .post(body)
        .build()
    val response = client.newCall(request).execute()
    val responseParsed = response.body!!.string()
    val responseString = Gson().fromJson(responseParsed, Entity::class.java)
    entityRequest.id = responseString.id
    println(response)
    println(response.body!!)
    println(responseParsed)
    println(entityRequest.id)
    return responseString
}
fun createRecord(pattern: List<String>, value: String): EntityPattern {
    return EntityPattern(0, "pattern", pattern, value)
}

fun getEntityRecord(name: String): List<EntityPattern> {
    val request = Request.Builder()
        .url("https://app.jaicp.com/cailapub/api/caila/p/b5bdcb48-76de-4c01-9f70-1408244c79b9/entities-by-names/$name/records")
        .get()
        .build()
    val response = client.newCall(request).execute()
    val responseParsed = response.body!!.string()
    val responseString = Gson().fromJson(responseParsed, EntityWithRecords::class.java)
    println(response)
    println(response.body!!)
    println(responseParsed)
    return responseString.records
}

fun addEntityRecord(entityID: Int, pattern: List<String>, value: String) {
    val entityPattern = EntityPattern(id = 0, type = "pattern", rule = pattern, value = value)
    val entityRequest = EntityRecord(data = entityPattern)
    val json: String = Gson().toJson(entityRequest)
    println(json)
    val body = RequestBody.create(("application/json; charset=utf-8".toMediaTypeOrNull()), json)
    val request = Request.Builder()
        .url("https://app.jaicp.com/cailapub/api/caila/p/b5bdcb48-76de-4c01-9f70-1408244c79b9/entities/$entityID/records")
        .post(body)
        .build()
    val response = client.newCall(request).execute()
    val responseParsed = response.body!!.string()
    val responseString = Gson().fromJson(responseParsed, EntityPattern::class.java)
    println(response)
    println(response.body!!)
    println(responseParsed)
}*/

fun cailaConform(text: String, number: Int, apiKey: String): String {
    val client = OkHttpClient()
    val url = URL("https://app.jaicp.com/cailapub/api/caila/p/$apiKey/nlu/conform?text=$text&number=$number")
    val request = Request.Builder()
        .url(url)
        .get()
        .build()
    val response = client.newCall(request).execute()

    return response.body!!.string()
}

fun main() {
    /* val checkEntity = createNamedEntity()
    if (checkEntity != null) {
        val checkEntityId = checkEntity.id
    }*/
    //val tagsEntityID = 77794
    //addEntityRecord(tagsEntityID, listOf("(юг*)"), "юг")
    //val checkEntityRecordId = 4646938
    //val intentID = 2474354
    /*
    val entityName = "tags"
    val x = addEntityRecord(ENTITY_NAME, listOf("(солнц*)"), "солнце")
    println(x)
    /*val records = getEntityRecord(entityName)
    for (each in records) {
        println(each.value)
    }*/

     */
    exportProject()
}