package com.justai.jaicf.template.connections

import com.justai.jaicf.channel.jaicp.JaicpServer
import com.justai.jaicf.channel.jaicp.channels.ChatApiChannel
import com.justai.jaicf.channel.jaicp.channels.ChatWidgetChannel
import com.justai.jaicf.channel.jaicp.channels.TelephonyChannel
import com.justai.jaicf.channel.telegram.TelegramChannel
import com.justai.jaicf.template.accessToken
import com.justai.jaicf.template.scripts.RANGE
import com.justai.jaicf.template.scripts.SHEET_ID
import com.justai.jaicf.template.scripts.parseTags
import com.justai.jaicf.template.scripts.updateTagsAndDestination
import com.justai.jaicf.template.demoBot
import java.util.*
import kotlin.concurrent.schedule

fun main() {
    // every 30 min check if there are any new recordings in googleSheets and add data to tags entity
    Timer("SettingUp", false).schedule(0, 1800000) {
        val tagsFromGoogleSheets = parseTags("https://sheet2bot.herokuapp.com/api/rows?sheet=$SHEET_ID&range=$RANGE")
        println(tagsFromGoogleSheets)
        updateTagsAndDestination(tagsFromGoogleSheets)
    }
    JaicpServer(
        botApi = demoBot,
        accessToken = accessToken,
        channels = listOf(
            ChatApiChannel,
            ChatWidgetChannel,
            TelephonyChannel,
            TelegramChannel
        )
    ).start(wait = false)
}