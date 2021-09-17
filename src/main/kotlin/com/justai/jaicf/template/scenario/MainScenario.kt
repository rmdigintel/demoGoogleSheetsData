package com.justai.jaicf.template.scenario

import com.justai.jaicf.activator.caila.*
import com.justai.jaicf.builder.Scenario
import com.justai.jaicf.template.scripts.TAGS_AND_THEIR_CITIES
import com.justai.jaicf.template.scripts.cailaConform


// var TAGS_AND_THEIR_CITIES: MutableMap<String, MutableList<String>> = mutableMapOf("море" to mutableListOf("Сочи", "Геленджик"), "юг" to mutableListOf("Астрахань", "Краснодар"))

val mainScenario = Scenario {

    state("start") {
        activators {
            regex("/start")
            intent("Hello")
        }
        action {
            reactions.say("Куда вы хотите отправиться?")
        }
    }

    state("suggestCity") {
        activators {
            intent("GetTag")
        }
        action {
            val tag = activator.caila?.slots?.get("tags")
            if (TAGS_AND_THEIR_CITIES.containsKey(tag)){
            //if (TAGS_AND_THEIR_CITIES[tag]){
                //val cities = TAGS_AND_THEIR_CITIES[tag]?.random()
                val cities = TAGS_AND_THEIR_CITIES[tag].toString()
                val amount = TAGS_AND_THEIR_CITIES[tag]?.size
                val input = request.input
                if (amount != null){
                    val cityConformed = cailaConform("город", amount, "b5bdcb48-76de-4c01-9f70-1408244c79b9")
                    reactions.say("По вашему запросу \"$input\" мне удалось найти $amount $cityConformed: $cities")
                }
                //reactions.say("Вы можете отправиться в $cities")
                else {
                    reactions.say("Я пока не знаю такого направления")
                }
            } else {
                reactions.say("Я пока не знаю такого направления")
            }
            reactions.go("/start")
        }
    }

    state("bye") {
        activators {
            intent("Bye")
        }
        action {
            reactions.say("Пока!")
        }
    }

    fallback {
        reactions.say("Я ничего не понял. Переформулируй свой запрос.")
    }
}
