package com.justai.jaicf.template.connections

import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.*
import kotlin.concurrent.schedule

/*
private val client = OkHttpClient()

var TAGS_AND_THEIR_CITIES: MutableMap<String, MutableList<String>> = mutableMapOf()
var MY_DATA: MutableMap<String, List<String>> = mutableMapOf()
var ALL_TAGS: MutableList<String> = mutableListOf()
var ALL_TAGS_PATTERNS: MutableList<String> = mutableListOf()

val VOWELS = listOf("а", "о", "у", "и", "е", "э", "ы", "я", "ю", "й")
val PREPOSITIONS = listOf("за", "под", "из", "от", "по", "для", "в", "на", "через", "о", "на")

fun makePattern(token_raw: String): String {
    val token = token_raw.trim()
    if (token == " ") {
        return ""
    }
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
            return if (token.substring(token.length - 1, token.length) == "ь") {
                val stem = token.substring(0, token.length - 1)
                "$stem*"
            } else {
                "$token*"
            }
        }
    } else {
        return if (token in PREPOSITIONS) {
            token
        } else {
            "$token*"
        }
    }
}

fun main() {
    // Timer("SettingUp", false).schedule(0, 10000) {
        val request = Request.Builder()
            .url("https://sheet2bot.herokuapp.com/api/rows?sheet=$SHEET_ID&range=$RANGE")
            .get()
            .build()
        val response = client.newCall(request).execute()
        // val responseParsed = response.body!!.string()
        val responseParsed = response.body!!.string().split("],[")
        for (each in responseParsed) {
            val eachParsed = (each.split("\",\""))
            val city = eachParsed[0].replace("\"", "").replace("[", "").replace("]", "")
            val tags = eachParsed[1].replace("\"", "").replace("[", "").replace("]", "").split(", ")
            val tagsLowerCase = mutableListOf<String>()
            for (tag_raw in tags) {
                val tag = tag_raw.trim().toLowerCase()
                if (!TAGS_AND_THEIR_CITIES.containsKey(tag)) {
                    TAGS_AND_THEIR_CITIES[tag] = mutableListOf<String>(city)
                } else {
                    TAGS_AND_THEIR_CITIES[tag]?.add(city)
                }
                tagsLowerCase.add(tag)
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
                }
                MY_DATA[city] = tagsLowerCase
            }
        }
        println(MY_DATA)
        println(ALL_TAGS)
        println(ALL_TAGS_PATTERNS)
        println(TAGS_AND_THEIR_CITIES)
    }

*/