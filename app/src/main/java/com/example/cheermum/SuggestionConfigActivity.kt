package com.example.cheermum

import android.os.Bundle
//import android.os.Environment
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.google.android.material.materialswitch.MaterialSwitch
import android.widget.EditText
import java.io.File
import org.json.JSONObject


class SuggestionConfigActivity : ComponentActivity(){
    //Information
    var suggestYogaValue: Boolean = true
    var suggestOutsideValue: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.suggestion_config )
    }
    override fun onResume(){
        super.onResume()
        setContentView(R.layout.suggestion_config)

        //More Information
        //val state: String? = Environment.getExternalStorageState()
        val yogaMessage: EditText = findViewById(R.id.yoga_message)
        val outsideMessage: EditText = findViewById(R.id.outside_message)
        var settingsSwitches = JSONObject(mapOf("yoga_suggestion" to true, "outside_suggestion" to true))
        var settingsText = JSONObject(mapOf("yoga_message" to yogaMessage.text.toString(), "outside_message" to outsideMessage.text.toString()))

        //Programmatically set values of switches and text when app is first launched
        val file = File(filesDir, "settingsSwitches.json")
        val fileText = File(filesDir, "settingsText.json")
        if(!file.exists() || !fileText.exists()){
            save(settingsSwitches, settingsText, yogaMessage.text.toString(), outsideMessage.text.toString())
        }
        settingsSwitches = JSONObject(file.inputStream().readBytes().toString(Charsets.UTF_8))
        settingsText = JSONObject(File(filesDir, "settingsText.json").inputStream().readBytes().toString(Charsets.UTF_8))

        //Text
        yogaMessage.setText(settingsText.getString("yoga_message"))
        outsideMessage.setText(settingsText.getString("outside_message"))

        //switches
        val yogaSwitch = findViewById<MaterialSwitch>(R.id.switch_yoga)
        val outsideSwitch = findViewById<MaterialSwitch>(R.id.switch_outside)
        yogaSwitch.isChecked = settingsSwitches.getBoolean("yoga_suggestion")
        outsideSwitch.isChecked = settingsSwitches.getBoolean("outside_suggestion")


        yogaSwitch.setOnCheckedChangeListener { _, isChecked ->
            suggestYogaValue = isChecked
        }
        outsideSwitch.setOnCheckedChangeListener { _, isChecked ->
            suggestOutsideValue = isChecked
        }

        //Submit settings button
        findViewById<Button>(R.id.Save).setOnClickListener {
            save(settingsSwitches, settingsText, yogaMessage.text.toString(), outsideMessage.text.toString())
        }

        //Go home button
        findViewById<Button>(R.id.Go_home).setOnClickListener {
            finish()
        }
    }
    fun save(settingsSwitches: JSONObject, settingsText: JSONObject, yogaMessage: String, outsideMessage: String){
        settingsSwitches.put("yoga_suggestion", suggestYogaValue)
        settingsSwitches.put("outside_suggestion", suggestOutsideValue)

        settingsText.put("yoga_message", yogaMessage)
        settingsText.put("outside_message", outsideMessage)

        var file = File(filesDir, "settingsSwitches.json")
        file.writeText(settingsSwitches.toString())

        file = File(filesDir, "settingsText.json")
        file.writeText(settingsText.toString())
    }
}