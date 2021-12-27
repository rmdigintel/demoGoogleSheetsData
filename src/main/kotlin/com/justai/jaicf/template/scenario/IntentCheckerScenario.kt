package com.justai.jaicf.template.scenario

import com.justai.jaicf.builder.Scenario
import com.justai.jaicf.template.scripts.API_KEY
import com.justai.jaicf.template.scripts.cailaGetInference

val intentCheckerScenario = Scenario {
    fallback{
        reactions.say(cailaGetInference(request.input, API_KEY).intent.path)
    }
}
