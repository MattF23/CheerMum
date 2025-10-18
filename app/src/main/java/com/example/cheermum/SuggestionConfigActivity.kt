package com.example.cheermum

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.google.android.material.materialswitch.MaterialSwitch
import android.widget.RadioGroup
import android.widget.Toast
import java.io.File
import org.json.JSONObject


class SuggestionConfigActivity : ComponentActivity(){
    //Information
    var sadDetectionValue: Boolean = true
    var angerDetectionValue: Boolean = true
    var angrySounds = "Violins"
    var sadnessSounds = "Whales"

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.suggestion_config )
    }
    override fun onResume(){
        super.onResume()
        setContentView(R.layout.suggestion_config)

        //More Information
        val angryRadioGroup = findViewById<RadioGroup>(R.id.angry_radio_group)
        val sadRadioGroup = findViewById<RadioGroup>(R.id.sad_radio_group)

        val file = File(filesDir, "settings.json")
        var settings = JSONObject(file.inputStream().readBytes().toString(Charsets.UTF_8))

        if(!file.exists()){
            save(settings,sadnessSounds, angrySounds)
        }
        else{
            settings = JSONObject(file.inputStream().readBytes().toString(Charsets.UTF_8))
        }

        setRadioButtons(settings)

        //switches
        val sadSwitch = findViewById<MaterialSwitch>(R.id.switch_sad)
        val angrySwitch = findViewById<MaterialSwitch>(R.id.switch_anger)
        sadSwitch.isChecked = settings.getBoolean("sadness_detection")
        angrySwitch.isChecked = settings.getBoolean("anger_detection")

        sadSwitch.setOnCheckedChangeListener { _, isChecked ->
            sadDetectionValue = isChecked
        }
        angrySwitch.setOnCheckedChangeListener { _, isChecked ->
            angerDetectionValue = isChecked
        }

        angryRadioGroup.setOnCheckedChangeListener {
            group, checkedId ->

            val angryRadioButton = findViewById<RadioButton>(checkedId)
            angrySounds = angryRadioButton.text.toString()
        }
        sadRadioGroup.setOnCheckedChangeListener {
            group, checkedId ->

            val sadRadioButton = findViewById<RadioButton>(checkedId)
            sadnessSounds = sadRadioButton.text.toString()
        }
        //Submit settings button
        findViewById<Button>(R.id.Save).setOnClickListener {
            save(settings,sadnessSounds, angrySounds)
        }

        //Go home button
        findViewById<Button>(R.id.Go_home).setOnClickListener {
            finish()
        }
    }
    fun save(settings: JSONObject, sadnessMusic: String, angryMusic: String){
        settings.put("sadness_detection", sadDetectionValue)
        settings.put("anger_detection", angerDetectionValue)

        settings.put("sadness_music", sadnessMusic)
        settings.put("angry_music", angryMusic)

        val file = File(filesDir, "settings.json")
        val message = settings.toString()
        file.writeText(message)

        val result = sendData(file)

        Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
    }
    fun setRadioButtons(settings: JSONObject){
        if(settings.get("sadness_music") == "Whales"){
            findViewById<RadioButton>(R.id.sad_whaleButton).setChecked(true)
            findViewById<RadioButton>(R.id.sad_violinButton).setChecked(false)
        }
        else if(settings.get("sadness_music") == "Violins"){
            findViewById<RadioButton>(R.id.sad_whaleButton).setChecked(false)
            findViewById<RadioButton>(R.id.sad_violinButton).setChecked(true)
        }
        if(settings.get("angry_music") == "Whales"){
            findViewById<RadioButton>(R.id.angry_whaleButton).setChecked(true)
            findViewById<RadioButton>(R.id.angry_violinButton).setChecked(false)
        }
        else if(settings.get("angry_music") == "Violins"){
            findViewById<RadioButton>(R.id.angry_whaleButton).setChecked(true)
            findViewById<RadioButton>(R.id.angry_violinButton).setChecked(false)
        }
    }
}