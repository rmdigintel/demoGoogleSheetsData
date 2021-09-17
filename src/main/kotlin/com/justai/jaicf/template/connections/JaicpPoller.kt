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
    Timer("SettingUp", false).schedule(0, 1800000) {
        var needToImport = false
        val tagsFromGoogleSheets = parseTags("https://sheet2bot.herokuapp.com/api/rows?sheet=$SHEET_ID&range=$RANGE")
        /*if (tagsFromGoogleSheets.isNotEmpty()) {
            println(tagsFromGoogleSheets)
            var project = exportProject()
            if (project != null) {
                println(project)
                // var entities = project?.entities
                val tagsRecordsValues: MutableList<String> = mutableListOf()
                for (i in 0..project.entities.size) {
                    if (project.entities[i].entity.name == "tags") {
                        for (each in project.entities[i].records) {
                            tagsRecordsValues += each.value
                        }
                        for (tag in tagsFromGoogleSheets){
                            if (tag.name !in tagsRecordsValues) {
                                needToImport = true
                                val record = createRecord(listOf(tag.pattern), tag.name)
                                project.entities[i].records.add(record)
                                println(record)
                                //addEntityRecord(ENTITY_ID, listOf(tag.pattern), tag.name)
                            }
                        }
                        break
                    }
                }
                if (needToImport){
                    importProject(project)
                }
            }
        }*/
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