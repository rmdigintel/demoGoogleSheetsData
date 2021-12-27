package com.justai.jaicf.template.connections

import com.justai.jaicf.channel.jaicp.JaicpPollingConnector
import com.justai.jaicf.channel.jaicp.channels.ChatApiChannel
import com.justai.jaicf.channel.jaicp.channels.ChatWidgetChannel
import com.justai.jaicf.channel.jaicp.channels.TelephonyChannel
import com.justai.jaicf.template.accessToken
import com.justai.jaicf.template.scripts.*
import com.justai.jaicf.template.templateBot
import java.util.*
import kotlin.concurrent.schedule

fun main() {
    // every 30 min check if there are any new recordings in googleSheets and add data to tags entity
    Timer("SettingUp", false).schedule(0, 1800000) {
        val tagsFromGoogleSheets = parseTags("https://sheet2bot.herokuapp.com/api/rows?sheet=$SHEET_ID&range=$RANGE")
        println(tagsFromGoogleSheets)
        updateTagsAndDestination(tagsFromGoogleSheets)
    }
    JaicpPollingConnector(
        templateBot,
        accessToken,
        channels = listOf(
            ChatApiChannel,
            ChatWidgetChannel,
            TelephonyChannel
        )
    ).runBlocking()
}

