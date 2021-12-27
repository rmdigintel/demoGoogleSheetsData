package com.justai.jaicf.template.scenario

import com.justai.jaicf.activator.caila.*
import com.justai.jaicf.builder.Scenario
import com.justai.jaicf.template.client
import com.justai.jaicf.template.scripts.API_KEY
//import com.justai.jaicf.template.scripts.TAGS_AND_THEIR_CITIES
import com.justai.jaicf.template.scripts.cailaConform
import com.justai.jaicf.test.context.runInTest
import com.mongodb.client.model.Filters
import java.awt.Point
import java.util.ArrayList

typealias CitiesAndAmount = Pair<String, Int>

val mainScenario = Scenario {

    state("start") {
        activators {
            regex("/start")
            intent("Hello")
        }
        activators ("/suggestCity") {
            intent("Yes")
        }
        action {
            reactions.sayRandom("Куда вы хотите отправиться?", "Какое место вы бы хотели посетить?", "Скажите, куда бы вы хотели отправиться?", "В каком месте вы хотите побывать?")
        }
    }

    state("suggestCity") {
        activators {
            intent("GetTag")
        }
        action{
            // get tag from data in entity
            var tag = activator.caila?.slots?.get("tags")
            runInTest {
                tag = getVar("tag") as? String
            }
            // find tag in mongo DB
            val filter = Filters.eq("tag", tag)
            val tagWithDestination = client.getDatabase("jaicf").getCollection("googleSheets").find(filter).first()
            //val destination = tagWithDestination?.getValue("destination") as ArrayList<*>
            val destination = if (tagWithDestination != null) tagWithDestination.getValue("destination") as ArrayList<*> else listOf()
            if (destination.isNotEmpty()) {
                val (cities, amount) = CitiesAndAmount(destination.joinToString(), destination.size)
                val input = request.input
                val cityConformed = cailaConform("город", amount, API_KEY)
                reactions.say("По вашему запросу \"$input\" мне удалось найти $amount $cityConformed: $cities")
            } else {
                reactions.say("Я пока не знаю подходящего места по вашему запросу. Может, вам интересно другое направление?")
            }
        }
    }

    state("thanks") {
        activators {
            intent("Thanks")
        }
        activators ("/suggestCity") {
            intent("Good")
        }
        action {
            reactions.say("Рад, что вам понравилось!")
        }
    }

    state("no") {
        activators {
            intent("No")
        }
        action {
            reactions.say("Очень жаль, что я не смог вам помочь!")
        }
    }

    state("bye") {
        activators {
            intent("Bye")
        }
        action {
            reactions.say("Обращайтесь снова! До встречи!")
        }
    }

    fallback {
        reactions.say("Я пока не знаю подходящего места по вашему запросу. Может, вам интересно другое направление?")
    }
}

