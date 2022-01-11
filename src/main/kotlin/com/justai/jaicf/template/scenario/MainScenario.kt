package com.justai.jaicf.template.scenario

import com.justai.jaicf.activator.caila.*
import com.justai.jaicf.builder.Scenario
import com.justai.jaicf.channel.jaicp.reactions.jaicp
import com.justai.jaicf.hook.*
import com.justai.jaicf.template.client
import com.justai.jaicf.template.scripts.*
import com.justai.jaicf.test.context.runInTest
import java.util.*

typealias CitiesAndAmount = Pair<String, Int>

val mainScenario = Scenario {
    handle<BotRequestHook> {
        if (!context.session.containsKey("helloMessage")) {
            reactions.say("Здравствуйте! Я помогу найти направление по вашему запросу.")
            context.session["helloMessage"] = true
        }
    }

    handle<ActionErrorHook> {
        reactions.say("Извините, возникла ошибка. Попробуйте еще раз")
    }

    state("start") {
        activators {
            regex("/start")
            intent("Hello")
        }
        activators("/help") {
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
            val tagWithDestination = findFirstDocument(tag, client.getDatabase("jaicf").getCollection("googleSheets"))
            val destination = if (tagWithDestination != null) tagWithDestination.getValue("destination") as ArrayList<String> else listOf()
            if (destination.isNotEmpty()) {
                val (cities, amount) = CitiesAndAmount(destination.sorted().joinToString(), destination.size)
                val input = request.input
                val cityConformed = cailaConform("город", amount, API_KEY)
                reactions.say("По вашему запросу \"$input\" мне удалось найти $amount $cityConformed: $cities")
                reactions.say("Вы довольны результатом поиска?")
            } else {
                reactions.go("/fallback")
            }
        }

        state("yes") {
            activators {
                intent("Thanks")
                intent("Good")
                intent("Yes")
            }
            action {
                reactions.say("Рад, что вам понравилось!")
                reactions.go("/help")
            }
        }

        state("no") {
            activators {
                intent("No")
            }
            action {
                reactions.say("Очень жаль:(")
                reactions.go("/help")
            }
        }
    }

    state("help") {
        action {
            reactions.say("Хотите найду для вас что-нибудь еще?")
        }
    }

    state("bye") {
        activators {
            intent("Bye")
        }
        activators("/help") {
            intent("No")
        }
        action {
            reactions.say("Обращайтесь снова! До встречи!")
            reactions.jaicp?.endSession()
            context.session.clear()
            context.client.clear()
        }
    }

    state("fallback") {
        activators {
            catchAll()
        }
        action {
            reactions.say("Я пока не знаю подходящего места по вашему запросу. Попробуйте ввести другое направление.")
        }
    }
}

