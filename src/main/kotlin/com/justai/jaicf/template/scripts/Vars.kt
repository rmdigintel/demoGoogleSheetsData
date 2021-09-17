package com.justai.jaicf.template.scripts

// GLOBAL VARS
var ENTITY_NAME = "tags"
var ENTITY_ID = 77794
var TAGS_AND_THEIR_CITIES: MutableMap<String, MutableList<String>> = mutableMapOf()
var MY_DATA: MutableMap<String, List<String>> = mutableMapOf()
var ALL_TAGS: MutableList<String> = mutableListOf()
var ALL_TAGS_PATTERNS: MutableList<String> = mutableListOf()
val VOWELS = listOf("а", "о", "у", "и", "е", "э", "ы", "я", "ю", "й")
val PREPOSITIONS = listOf("за", "под", "из", "от", "по", "для", "в", "на", "через", "о", "на")

// Google
const val SHEET_ID = "1UGSi41MDJ3hHaI27uNDCPhmsBhiAd4zeRpTaZlUIBYs"
// const val SHEET_NAME = "demobotData"
const val RANGE = "A2:B50"
