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
//data class Paging(val pageNum: Int, val pageSize: Int, val totalCount: Int)
data class EntityWithRecords(var entity: Entity, var records: MutableList<EntityPattern>)
//data class EntityRecord(var data: EntityPattern, var clientId: Any? = null)
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
data class Intent(
    val id: Int,
    val path: String,
    val answer: Any,
    val customData: Any,
    val slots: Any,
    val priority: Int)
data class GetIntent(
    val intent: Intent,
    val confidence: Double,
    val slots: List<Any>,
    val debug: Any
)

fun createRecord(pattern: List<String>, value: String): EntityPattern {
    return EntityPattern(0, "pattern", pattern, value)
}

fun exportProject(): Project? {
    val body = RequestBody.create(null, byteArrayOf())
    val request = Request.Builder()
        .url("https://app.jaicp.com/cailapub/api/caila/p/$API_KEY/export")
        .post(body)
        .build()
    val response = client.newCall(request).execute()
    val responseParsed = response.body!!.string()
    return Gson().fromJson(responseParsed, Project::class.java)
}

fun importProject(project: Project) {
    val json: String = Gson().toJson(project)
    val body = RequestBody.create(("application/json; charset=utf-8".toMediaTypeOrNull()), json)
    val request = Request.Builder()
        .url("https://app.jaicp.com/cailapub/api/caila/p/$API_KEY/import")
        .post(body)
        .build()
    val response = client.newCall(request).execute()
    val responseParsed = response.body!!.string()
    println(Gson().fromJson(responseParsed, Project::class.java))
}

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

fun cailaGetInference(text: String, apiKey: String): GetIntent {
    val client = OkHttpClient()
    val url = URL("https://app.jaicp.com/cailapub/api/caila/p/$apiKey/nlu/inference?query=$text")
    val request = Request.Builder()
        .url(url)
        .get()
        .build()
    val response = client.newCall(request).execute()
    val responseParsed = response.body!!.string()
    return Gson().fromJson(responseParsed, GetIntent::class.java)
}

