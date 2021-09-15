package com.justai.jaicf.template.scenario

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.*
import kotlinx.coroutines.coroutineScope
import org.slf4j.LoggerFactory
import java.io.FileInputStream
import java.util.Timer
import kotlin.concurrent.schedule

//GLOBAL VARS
//const val SHEET_ID = "1UGSi41MDJ3hHaI27uNDCPhmsBhiAd4zeRpTaZlUIBYs"
//const val SHEET_NAME = "demobotData"
//const val RANGE = "B2:B50"

//@Serializable
//data class Customer(inner class query =  )
/*
suspend fun main() {
    val client = HttpClient(CIO)
    val range = "'$SHEET_NAME'!$RANGE"
    val response: HttpResponse = client.get("https://sheet2bot.herokuapp.com/api/rows?sheet=${SHEET_ID}&range=${range}") {
    }
    println(response)
}*/
/*
val client = HttpClient(CIO) {
    install(JsonFeature) {}
}
val range = "'" + SHEET_NAME + "'!" + RANGE
val response: HttpResponse = client.get("https://sheet2bot.herokuapp.com/api/rows?sheet=${SHEET_ID}&range=${range}") {
}*/
/*
val timer = Timer("SettingUp", false).schedule(0, 100000) {
    launch { getData() }
    val range = "'" + SHEET_NAME + "'!" + RANGE
    var res = getData(range)
    var link = "https://sheet2bot.herokuapp.com/api/rows?sheet=${SHEET_ID}&range=${RANGE}";

    }
}
//timer.start()

*/