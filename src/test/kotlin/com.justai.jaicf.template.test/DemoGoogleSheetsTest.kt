package com.justai.jaicf.template.test

import com.justai.jaicf.template.scenario.intentCheckerScenario
import com.justai.jaicf.template.scenario.mainScenario
import com.justai.jaicf.test.ScenarioTest
import org.junit.jupiter.api.Test


class DemoGoogleSheetsTest: ScenarioTest(mainScenario) {
    @Test
    fun `TagExists `() {
        query("/start") responds "Куда вы хотите отправиться?"
        withVariables("tag" to "testUnit")
        intent("GetTag") responds "По вашему запросу \"GetTag\" мне удалось найти 2 города: City1, City2"
    }

    @Test
    fun `TagDoesn'tExist`() {
        withCurrentState("/start")
        withVariables("tag" to "noTag")
        intent("GetTag") responds "Я пока не знаю подходящего места по вашему запросу. Может, вам интересно другое направление?"
    }

    @Test
    fun `Thanks `() {
        withCurrentState("/suggestCity")
        intent("Good") endsWithState ("/thanks")
    }

}

