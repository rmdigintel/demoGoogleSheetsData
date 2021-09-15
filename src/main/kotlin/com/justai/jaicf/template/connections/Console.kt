package com.justai.jaicf.template.connections

import io.ktor.utils.io.errors.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import okhttp3.*
import java.util.*
import kotlin.concurrent.schedule
import com.fasterxml.jackson.module.kotlin.*


/*
fun main() {
    var str = "[[\"Направление\",\"Теги для поиска\"],[\"Анапа\",\"Море, пляж, солнце, курорт, отдых, загорать, купаться, горы, поход, Черное море, по России, юг\"],[\"Анталья\",\"Море, пляж, солнце, курорт, отдых, загорать, купаться, Черное море, Турция, юг\"],[\"Афины\",\"Море, пляж, солнце, курорт, отдых, загорать, купаться, Черное море, Греция, юг\"],[\"Бейрут\",\"Солнце, Ливан\"],[\"Белград\",\"Сербия, Европа, Дунай\"],[\"Варадеро\",\"Море, пляж, солнце, курорт, отдых, загорать, купаться, Куба, юг\"],[\"Владивосток\",\"Океан, Дальний Восток\"],[\"Волгоград\",\"Волга, по России,  Юг\"],[\"Геленджик\",\"Море, пляж, солнце, курорт, отдых, загорать, купаться, горы, поход, Черное море, по России, юг\"],[\"Даламан\",\"Море, пляж, солнце, курорт, отдых, загорать, купаться, Черное море, Турция, юг\"],[\"Дубай\",\"Море, пляж, солнце, курорт, отдых, загорать, купаться, океан, Дубай, ОАЭ, юг\"],[\"Екатеринбург\",\"Урал,  по России\"],[\"Ираклион\",\"Море, пляж, солнце, курорт, отдых, загорать, купаться, Греция, Крит, юг\"],[\"Казань\",\"Волга, по России\"],[\"Калининград\",\"Кенигсберг,  Куршский залив, Куршская Коса, Балтийское море\"],[\"Касабланка\",\"Море, пляж, солнце, курорт, отдых, загорать, купаться, Марокко\"],[\"Краснодар\",\"Море, пляж, солнце, курорт, отдых, загорать, купаться, горы, поход, дикая природа, Черное море, по России\"],[\"Красноярск\",\"Сибирь, по России, Енисей\"],[\"Лиссабон\",\"Море, пляж, солнце, курорт, отдых, загорать, купаться, океан, Португалия\"],[\"Мале\",\"Море, пляж, солнце, курорт, отдых, загорать, купаться, океан, Мальдивы\"],[\"Нижний Новгород\",\"Ока, Волга,  по России\"],[\"Новосибирск\",\"Сибирь, по России, Обь\"],[\"Омск\",\"Сибирь, по России, Иртыш\"],[\"Петропавловск-Камчатский\",\"Океан, камчатка, вулканы, поход, дикая природа\"],[\"Ростов-на-Дону\",\"Солнце, по России, Дон,  Юг\"],[\"Салоники\",\"Море, пляж, солнце, курорт, отдых, загорать, купаться, Греция, Термаикос, юг\"],[\"Самара\",\"Волга, на рыбалку, по России, Поволжье, пляж\"],[\"Санкт-Петербург\",\"Север, Финский залив, Нева, по России\"],[\"Сейшелы\",\"Океан, пляж, солнце, курорт, отдых, загорать, купаться, океан, Юг\"],[\"Симферополь\",\"Море, пляж, солнце, курорт, отдых, загорать, купаться, горы, поход, дикая природа, Крым, Черное море, по России, Юг\"],[\"София\",\"Солнце, курорт, отдых, загорать, Болгария, Юг\"],[\"Сочи\",\"Море, пляж, солнце, курорт, отдых, загорать, купаться, горы, поход, дикая природа, Черное море, по России, Юг\"],[\"Стамбул\",\"Море, пляж, солнце, курорт, отдых, загорать, купаться, Черное море, Турция, Юг\"],[\"Хабаровск\",\"Дальний Восток, Амур, по России\"],[\"Челябинск\",\"Урал, по России\"],[\"Южно-Сахалинск\",\"Океан, вулканы, поход, дикая природа, Дальний Восток\"]]"
    val re = "[^A-Za-z0-9]"
    str = str.replace(re, " ")
    println(str)
    println("ok")
}*/

/*
@Serializable
data class Project(val name: String, val language: String)

fun main() {
    val data = Json.decodeFromString<Project>("""
        {"name":"kotlinx.serialization","language":"Kotlin"}
    """)
    println(data)
}*/






// GET запросы к API по таймеру
const val SHEET_ID = "1UGSi41MDJ3hHaI27uNDCPhmsBhiAd4zeRpTaZlUIBYs"
// const val SHEET_NAME = "demobotData"
const val RANGE = "A2:B50"
var DATA = mutableMapOf<String, Any?>()


private val client = OkHttpClient()

fun main() {
    Timer("SettingUp", false).schedule(0, 100000) {
        val request = Request.Builder()
            .url("https://sheet2bot.herokuapp.com/api/rows?sheet=$SHEET_ID&range=$RANGE")
            .get()
            .build()
        val response = client.newCall(request).execute()
        val responseParsed = response.body!!.string()
        //val responseParsed = response.body!!.string().split("],[")
        /*for (each in responseParsed) {
            println(each)
        }*/

        val json = jacksonObjectMapper()  // keep around and re-use
        val myList: List<String> = json.readValue(responseParsed)
        println(myList)

        /*client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            // (response.body!!.string())
            println(response.body!!.)*/
        /*for ((name, value) in response.headers) {
                println("$name: $value")
            }*/
        /*
            val data = (response.body!!.string())
            val re = "[[]\"]".toRegex()
            var str = re.replace(str, "")
            for (each in data) {
                each.split("")
            }
            if (DATA.isEmpty()) {

            } else {

            }
            for (each in data) {
                if (each[0] in DATA.keys )
            }
            println(data)
            */

    }
}
