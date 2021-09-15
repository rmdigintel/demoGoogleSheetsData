package com.justai.jaicf.template.scenario

import com.google.gson.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull

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

// type = "pattern" || "synonyms"
data class EntityPattern(
    var id: Int,
    var type: String,
    var rule: List<String>,
    var value: String = "",
    var clientId: Any? = null)

data class EntityRecord(var data: EntityPattern, var clientId: Any? = null)

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
    var testModel = Gson().fromJson(responseParsed, Entity::class.java)
    entityRequest.id = testModel.id
    println(response)
    println(response.body!!)
    println(responseParsed)
    println(entityRequest.id)
    return testModel
}

fun addEntityRecord(entityId: Int, pattern: List<String>): EntityPattern? {
    val entityPattern = EntityPattern(id = 0, type = "pattern", rule = pattern)
    val entityRequest = EntityRecord(data = entityPattern)
    val json: String = Gson().toJson(entityRequest)
    println(json)
    val body = RequestBody.create(("application/json; charset=utf-8".toMediaTypeOrNull()), json)
    val request = Request.Builder()
        .url("https://app.jaicp.com/cailapub/api/caila/p/b5bdcb48-76de-4c01-9f70-1408244c79b9/entities/$entityId/records")
        .post(body)
        .build()
    val response = client.newCall(request).execute()
    val responseParsed = response.body!!.string()
    val testModel = Gson().fromJson(responseParsed, EntityPattern::class.java)
    println(response)
    println(response.body!!)
    println(responseParsed)
    return testModel
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

fun main() {
    /* val checkEntity = createNamedEntity()
    if (checkEntity != null) {
        val checkEntityId = checkEntity.id
    }*/
    val checkEntityId = 77737
    //addEntityRecord(77737, listOf("(проверк* работ*)"))
    val checkEntityRecordId = 4646938
}