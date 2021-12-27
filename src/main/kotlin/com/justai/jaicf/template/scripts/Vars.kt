package com.justai.jaicf.template.scripts

import okhttp3.OkHttpClient
import okhttp3.Request
import com.londogard.nlp.stemmer.Stemmer
import com.londogard.nlp.utils.LanguageSupport

// GLOBAL VARS
var ENTITY_NAME = "tags"
//var TAGS_AND_THEIR_CITIES: MutableMap<String, MutableList<String>> = mutableMapOf()
//var MY_DATA: MutableMap<String, List<String>> = mutableMapOf()
//var ALL_TAGS: MutableList<String> = mutableListOf()
//var ALL_TAGS_PATTERNS: MutableList<String> = mutableListOf()
val VOWELS = listOf("а", "о", "у", "и", "е", "э", "ы", "я", "ю")
val PREPOSITIONS = listOf("за", "под", "из", "от", "по", "для", "в", "на", "через", "о", "на")
val API_KEY = "b5bdcb48-76de-4c01-9f70-1408244c79b9"

// Google
const val SHEET_ID = "1UGSi41MDJ3hHaI27uNDCPhmsBhiAd4zeRpTaZlUIBYs"
// const val SHEET_NAME = "demobotData"
const val RANGE = "A2:B50"

fun main() {
    /*val client = OkHttpClient()
    val request = Request.Builder()
        .url("https://sheet2bot.herokuapp.com/api/rows?sheet=$SHEET_ID&range=$RANGE")
        .get()
        .build()
    val response =  client.newCall(request).execute()
    val responseString = response.body!!.string()
    val result: List<String> = responseString.substring(1,responseString.length -1).split("],[").map { it.trim() }
    println(result)
    //result.forEach { println(it) }*/

    val stemmer = Stemmer(LanguageSupport.ru)
    println(stemmer.stem("оаэ")+"*")

}