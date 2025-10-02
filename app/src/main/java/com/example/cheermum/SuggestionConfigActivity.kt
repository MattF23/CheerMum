package com.example.cheermum

import android.os.Bundle
import android.os.Environment
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.google.android.material.materialswitch.MaterialSwitch
import android.widget.Toast
import android.widget.EditText
import org.json.JSONArray
import org.json.JSONObject
import java.io.File


class SuggestionConfigActivity : ComponentActivity(){
    var isAvailable = false
    var isWritable = false
    var isReadable = false

    //Information
    val settingsSwitchesPath = "settingsSwitches.json"
    val settingsTextPath = "settingsText.json"
    val settingsSwitchesFile = File(settingsSwitchesPath)
    val settingsTextFile = File(settingsTextPath)
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

        //Environment
        val state: String? = Environment.getExternalStorageState()
        val yogaMessage: EditText = findViewById(R.id.yoga_message)
        val outsideMessage: EditText = findViewById(R.id.outside_message)
        var settingsSwitches = mapOf("yoga_suggestions" to true, "outside_suggestions" to true).toMutableMap()
        var settingsText = mapOf("yoga_message" to yogaMessage.text.toString(), "outside_message" to outsideMessage.text.toString()).toMutableMap()

        if(settingsSwitchesFile.exists() && settingsTextFile.exists()){
            val jsonObj = JSONObject(settingsSwitchesFile.readText())
            //settingsSwitches = decodeFromsString(jsonObj)

            val jsonTextString = settingsSwitchesFile.readText()

        }

        //switches
        findViewById<MaterialSwitch>(R.id.switch_yoga).setOnCheckedChangeListener { _, isChecked ->
            suggestYogaValue = isChecked
            Toast.makeText(this, suggestYogaValue.toString(), Toast.LENGTH_SHORT).show()
        }
        findViewById<MaterialSwitch>(R.id.switch_outside).setOnCheckedChangeListener { _, isChecked ->
            suggestOutsideValue = isChecked
            Toast.makeText(this, suggestOutsideValue.toString(), Toast.LENGTH_SHORT).show()
        }

        //Submit settings button
        findViewById<Button>(R.id.Save).setOnClickListener {
            save(settingsSwitches, settingsText, yogaMessage.text.toString(), outsideMessage.text.toString())

            //Check if usb plugged in and available
            if(Environment.MEDIA_MOUNTED == state){
                //Can read and write
                isAvailable = true
                isWritable = true
                isReadable = true
            }
            else if(Environment.MEDIA_MOUNTED_READ_ONLY == state){
                //Can read
                isAvailable= true
                isWritable= false
                isReadable= true
            }
            else{
                //Not available
                isAvailable = false
                isWritable = false
                isReadable = false
            }
        }

        //Go home button
        findViewById<Button>(R.id.Go_home).setOnClickListener {
            finish()
        }
    }
    fun save(settingsSwitches: MutableMap<String, Boolean>, settingsText: MutableMap<String, String>, yogaMessage: String, outsideMessage: String){
        settingsSwitches.put("yoga_suggestions", suggestYogaValue)
        settingsSwitches.put("outside_suggestions", suggestOutsideValue)

        settingsText.put("yoga_message", yogaMessage)
        settingsText.put("outside_message", outsideMessage)

        File("settingsSwitches.json").writeText(settingsSwitches.toString())
        File("settingsText.json").writeText(settingsText.toString())
    }
}