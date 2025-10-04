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
        //Default settings
        var settings = JSONObject(mapOf("yoga_suggestion" to true, "outside_suggestion" to true,
            "yoga_message" to yogaMessage.text.toString(), "outside_message" to outsideMessage.text.toString()))

        //Programmatically set values of switches and text when app is first launched
        val file = File(filesDir, "settings.json")
        if(!file.exists()){
            save(settings,yogaMessage.text.toString(), outsideMessage.text.toString())
        }
        settings = JSONObject(file.inputStream().readBytes().toString(Charsets.UTF_8))

        //Text
        yogaMessage.setText(settings.getString("yoga_message"))
        outsideMessage.setText(settings.getString("outside_message"))

        //switches
        val yogaSwitch = findViewById<MaterialSwitch>(R.id.switch_yoga)
        val outsideSwitch = findViewById<MaterialSwitch>(R.id.switch_outside)
        yogaSwitch.isChecked = settings.getBoolean("yoga_suggestion")
        outsideSwitch.isChecked = settings.getBoolean("outside_suggestion")

        yogaSwitch.setOnCheckedChangeListener { _, isChecked ->
            suggestYogaValue = isChecked
        }
        outsideSwitch.setOnCheckedChangeListener { _, isChecked ->
            suggestOutsideValue = isChecked
        }

        //Submit settings button
        findViewById<Button>(R.id.Save).setOnClickListener {
            save(settings,yogaMessage.text.toString(), outsideMessage.text.toString())
        }

        //Go home button
        findViewById<Button>(R.id.Go_home).setOnClickListener {
            finish()
        }
    }
    fun save(settings: JSONObject, yogaMessage: String, outsideMessage: String){
        settings.put("yoga_suggestion", suggestYogaValue)
        settings.put("outside_suggestion", suggestOutsideValue)

        settings.put("yoga_message", yogaMessage)
        settings.put("outside_message", outsideMessage)

        val file = File(filesDir, "settings.json")
        val message = settings.toString()
        file.writeText(message)

        sendData(this, message)
    }
}