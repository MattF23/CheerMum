package com.example.cheermum

import android.os.Bundle
//import android.os.Environment
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.google.android.material.materialswitch.MaterialSwitch
import android.widget.Toast
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
        val settingsSwitches = mapOf("yoga_suggestion" to true, "outside_suggestion" to true).toMutableMap()
        val settingsText = mapOf("yoga_message" to yogaMessage.text.toString(), "outside_message" to outsideMessage.text.toString()).toMutableMap()

        //Programmatically set values of switches and text when app is first launched
        val file = File(filesDir, "settingsSwitches.json")
        if(!file.exists()){
            save(settingsSwitches, settingsText, yogaMessage.text.toString(), outsideMessage.text.toString())
        }
        val settingsSwitchesTemp = read(file)

        //switches
        val yogaSwitch = findViewById<MaterialSwitch>(R.id.switch_yoga)
        val outsideSwitch = findViewById<MaterialSwitch>(R.id.switch_outside)
        yogaSwitch.isChecked = settingsSwitchesTemp.getBoolean("yoga_suggestion")
        outsideSwitch.isChecked = settingsSwitchesTemp.getBoolean("outside_suggestion")


        yogaSwitch.setOnCheckedChangeListener { _, isChecked ->
            suggestYogaValue = isChecked
            Toast.makeText(this, suggestYogaValue.toString(), Toast.LENGTH_SHORT).show()
        }
        outsideSwitch.setOnCheckedChangeListener { _, isChecked ->
            suggestOutsideValue = isChecked
            Toast.makeText(this, suggestOutsideValue.toString(), Toast.LENGTH_SHORT).show()
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
    fun save(settingsSwitches: MutableMap<String, Boolean>, settingsText: MutableMap<String, String>, yogaMessage: String, outsideMessage: String){
        settingsSwitches.put("yoga_suggestion", suggestYogaValue)
        settingsSwitches.put("outside_suggestion", suggestOutsideValue)

        settingsText.put("yoga_message", yogaMessage)
        settingsText.put("outside_message", outsideMessage)

        val file = File(filesDir, "settingsSwitches.json")
        file.writeText(settingsSwitches.toString())
    }
    fun read(file: File): JSONObject{
        Toast.makeText(this, file.inputStream().readBytes().toString(Charsets.UTF_8), Toast.LENGTH_LONG).show()

        return JSONObject(file.inputStream().readBytes().toString(Charsets.UTF_8))
    }
}