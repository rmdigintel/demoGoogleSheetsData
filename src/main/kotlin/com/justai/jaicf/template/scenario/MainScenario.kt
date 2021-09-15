package com.justai.jaicf.template.scenario

import com.justai.jaicf.builder.Scenario

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
