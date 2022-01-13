package com.justai.jaicf.template.test

import com.justai.jaicf.template.scenario.intentCheckerScenario
import com.justai.jaicf.template.scenario.mainScenario
import com.justai.jaicf.test.ScenarioTest
import org.junit.jupiter.api.Test


class DemoGoogleSheetsTest: ScenarioTest(mainScenario) {
    @Test
    fun `TagExists Pleased`() {
        query("/start") responds "Здравствуйте! Я помогу найти направление по вашему запросу.\n" + "Куда вы хотите отправиться?"
        withVariables("tag" to "testUnit")
        intent("GetTag") responds "По вашему запросу \"GetTag\" мне удалось найти 2 города: City1, City2\n" + "Вы довольны результатом поиска?"
        intent("Yes") responds "Рад, что вам понравилось!\n" + "Хотите найду для вас что-нибудь еще?"
    }

    @Test
    fun `TagExists NotPleased`() {
        query("/start") responds "Здравствуйте! Я помогу найти направление по вашему запросу.\n" + "Куда вы хотите отправиться?"
        withVariables("tag" to "testUnit")
        intent("GetTag") responds "По вашему запросу \"GetTag\" мне удалось найти 2 города: City1, City2\n" + "Вы довольны результатом поиска?"
        intent("No") responds "Очень жаль:(\n" + "Хотите найду для вас что-нибудь еще?"
    }

    @Test
    fun `TagDoesn'tExist`() {
        query("/start") responds "Здравствуйте! Я помогу найти направление по вашему запросу.\n" + "Куда вы хотите отправиться?"
        withVariables("tag" to "noTag")
        intent("GetTag") responds "Я пока не знаю подходящего места по вашему запросу.\n" + "Хотите найду для вас что-нибудь еще?"
    }

}

