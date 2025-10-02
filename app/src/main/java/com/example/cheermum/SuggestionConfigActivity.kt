package com.example.cheermum

import android.os.Bundle
import android.os.Environment
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.google.android.material.materialswitch.MaterialSwitch
import android.widget.Toast
import android.widget.EditText
import org.json.JSONObject
import java.io.File


class SuggestionConfigActivity : ComponentActivity(){
    //Environment
    var state: String? = Environment.getExternalStorageState()
    var isAvailable = false
    var isWritable = false
    var isReadable = false

    //Information
    val settingsPath = "settings.json"
    val settingsFile = File(settingsPath)
    var suggestYogaValue: Boolean = true
    var suggestOutsideValue: Boolean = true
    val yogaMessageText: EditText = findViewById(R.id.yoga_message)
    var yogaMessage =  yogaMessageText.text.toString()
    val outsideMessageText: EditText = findViewById(R.id.outside_message)
    var outsideMessage = outsideMessageText.text.toString()
    var settingsSwitches = mapOf("yoga_suggestions" to true, "outside_suggestions" to true).toMutableMap()
    var settingsText = mapOf("yoga_message" to yogaMessage, "outside_message" to outsideMessage).toMutableMap()


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.suggestion_config )
    }
    override fun onResume(){
        super.onResume()
        setContentView(R.layout.suggestion_config)

        if(settingsFile.exists()){
            val jsonString = settingsFile.readText()

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
            val arrayOfJsons = getJsons()

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

    override fun onDestroy() {
        super.onDestroy()
        val jsons = getJsons()
    }

    fun getJsons(): Array<JSONObject>{
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
}