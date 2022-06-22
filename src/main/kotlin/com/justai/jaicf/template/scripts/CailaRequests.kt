package com.justai.jaicf.template.scripts

import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.net.URL

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
data class EntityWithRecords(var entity: Entity, var records: MutableList<EntityPattern>)
data class Project(
    var project: Any,
    var settings: Any,
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
    val url = "https://app.jaicp.com/cailapub/api/caila/p/$API_KEY/export"
    val responseParsed = postRequest(url, body)
    return Gson().fromJson(responseParsed, Project::class.java)
}

fun importProject(project: Project) {
    val JSON = MediaType.parse("application/json; charset=utf-8")
    val json: String = Gson().toJson(project)
    val body = RequestBody.create(JSON, json)
    val url = "https://app.jaicp.com/cailapub/api/caila/p/$API_KEY/import"
    val responseParsed = postRequest(url, body)
    println(Gson().fromJson(responseParsed, Project::class.java))
}

fun cailaConform(text: String, number: Int, apiKey: String): String? {
    val url = "https://app.jaicp.com/cailapub/api/caila/p/$apiKey/nlu/conform?text=$text&number=$number"
    val response = getRequest(url)
    return response!!
}

fun cailaGetInference(text: String, apiKey: String): GetIntent {
    val url = "https://app.jaicp.com/cailapub/api/caila/p/$apiKey/nlu/inference?query=$text"
    val response = getRequest(url)
    return Gson().fromJson(response, GetIntent::class.java)
}

fun main() {
    val project = exportProject()
    if (project != null) {
        importProject(project)
    }
}
