package com.justai.jaicf.template

import com.justai.jaicf.BotEngine
import com.justai.jaicf.activator.caila.CailaIntentActivator
import com.justai.jaicf.activator.caila.CailaNLUSettings
import com.justai.jaicf.activator.regex.RegexActivator
import com.justai.jaicf.channel.jaicp.logging.JaicpConversationLogger
import com.justai.jaicf.context.manager.mongo.MongoBotContextManager
import com.mongodb.client.MongoClients
import com.justai.jaicf.logging.Slf4jConversationLogger
import com.justai.jaicf.template.scenario.mainScenario
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import org.bson.Document
import java.util.*

val accessToken: String = System.getenv("JAICP_API_TOKEN") ?: Properties().run {
    load(CailaNLUSettings::class.java.getResourceAsStream("/jaicp.properties"))
    getProperty("apiToken")
}

// var client: MongoClient = MongoClients.create("mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&directConnection=true&ssl=false")
//var client: MongoClient = MongoClients.create("mongodb+srv://cluster0.4xxrk.mongodb.net/myFirstDatabase?authSource=%24external&authMechanism=MONGODB-X509&retryWrites=true&w=majority")
var client: MongoClient = MongoClients.create("mongodb+srv://kotlin:y75ZVsGq78rFvIeA@cluster0.4xxrk.mongodb.net/test")
val myCollection: MongoCollection<Document> = client.getDatabase("jaicf").getCollection("googleSheets")

private val cailaNLUSettings = CailaNLUSettings(
    accessToken = accessToken

)

val templateBot = BotEngine(
    scenario = mainScenario,
    conversationLoggers = arrayOf(
        JaicpConversationLogger(accessToken),
        Slf4jConversationLogger()
    ),
    activators = arrayOf(
        CailaIntentActivator.Factory(cailaNLUSettings),
        RegexActivator
    )
)
