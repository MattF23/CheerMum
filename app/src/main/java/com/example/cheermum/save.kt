package com.example.cheermum

import org.json.JSONObject

fun getJsons(settingsText: MutableMap<String, Boolean>): Array<JSONObject>{
    settingsSwitches.put("yoga_suggestions", suggestYogaValue)
    settingsSwitches.put("outside_suggestions", suggestOutsideValue)

    yogaMessage = yogaMessageText.text.toString()
    outsideMessage = outsideMessageText.text.toString()
    settingsText.put("yoga_message", yogaMessage)
    settingsText.put("outside_message", outsideMessage)

    val switchJson = JSONObject(settingsSwitches)
    val textJson = JSONObject(settingsText)

    return arrayOf(switchJson, textJson)
}