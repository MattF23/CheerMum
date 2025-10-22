package com.example.cheermum

import android.media.AudioManager
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
    var happyDetectionValue: Boolean = true
    var angrySounds = "Violins"
    var sadnessSounds = "Whales"
    var happySounds = "Bus"

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
        val happyRadioGroup = findViewById<RadioGroup>(R.id.happy_radio_group)

        val file = File(filesDir, "settings.json")
        var settings = JSONObject(file.inputStream().readBytes().toString(Charsets.UTF_8))

        if(!file.exists()){
            save(settings,sadnessSounds, angrySounds, happySounds)
        }
        else{
            settings = JSONObject(file.inputStream().readBytes().toString(Charsets.UTF_8))
        }

        setRadioButtons(settings)

        //switches
        val sadSwitch = findViewById<MaterialSwitch>(R.id.switch_sad)
        val angrySwitch = findViewById<MaterialSwitch>(R.id.switch_anger)
        val happySwitch = findViewById<MaterialSwitch>(R.id.switch_happy)
        sadSwitch.isChecked = settings.getBoolean("sadness_detection")
        angrySwitch.isChecked = settings.getBoolean("anger_detection")
        happySwitch.isChecked = settings.getBoolean("happiness_detection")

        sadSwitch.setOnCheckedChangeListener { _, isChecked ->
            sadDetectionValue = isChecked
        }
        angrySwitch.setOnCheckedChangeListener { _, isChecked ->
            angerDetectionValue = isChecked
        }
        happySwitch.setOnCheckedChangeListener { _, isChecked ->
            happyDetectionValue = isChecked
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
        happyRadioGroup.setOnCheckedChangeListener {
                group, checkedId ->

            val happyRadioButton = findViewById<RadioButton>(checkedId)
            sadnessSounds = happyRadioButton.text.toString()
        }
        //Submit settings button
        findViewById<Button>(R.id.Save).setOnClickListener {
            save(settings,sadnessSounds, angrySounds, happySounds)
        }

        //Go home button
        findViewById<Button>(R.id.Go_home).setOnClickListener {
            finish()
        }
    }
    fun save(settings: JSONObject, sadnessMusic: String, angryMusic: String, happyMusic: String){
        //Get volume
        val audio = getSystemService(AUDIO_SERVICE) as AudioManager
        val volume = (audio.getStreamVolume(AudioManager.STREAM_MUSIC) * 6.6).toInt().toString() + "%"

        settings.put("sadness_detection", sadDetectionValue)
        settings.put("anger_detection", angerDetectionValue)
        settings.put("happiness_detection", happyDetectionValue)

        settings.put("sadness_music", sadnessMusic)
        settings.put("angry_music", angryMusic)
        settings.put("happy_music", happyMusic)

        settings.put("Volume", volume)

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
            findViewById<RadioButton>(R.id.sad_busButton).setChecked(false)
        }
        else if(settings.get("sadness_music") == "Violins"){
            findViewById<RadioButton>(R.id.sad_whaleButton).setChecked(false)
            findViewById<RadioButton>(R.id.sad_violinButton).setChecked(true)
            findViewById<RadioButton>(R.id.sad_busButton).setChecked(false)
        }
        else if(settings.get("sadness_music") == "bus"){
            findViewById<RadioButton>(R.id.sad_whaleButton).setChecked(false)
            findViewById<RadioButton>(R.id.sad_violinButton).setChecked(false)
            findViewById<RadioButton>(R.id.sad_busButton).setChecked(true)
        }

        if(settings.get("angry_music") == "Whales"){
            findViewById<RadioButton>(R.id.angry_whaleButton).setChecked(true)
            findViewById<RadioButton>(R.id.angry_violinButton).setChecked(false)
            findViewById<RadioButton>(R.id.angry_busButton).setChecked(false)
        }
        else if(settings.get("angry_music") == "Violins"){
            findViewById<RadioButton>(R.id.angry_whaleButton).setChecked(true)
            findViewById<RadioButton>(R.id.angry_violinButton).setChecked(false)
            findViewById<RadioButton>(R.id.angry_busButton).setChecked(false)
        }
        else if(settings.get("angry_music") == "bus"){
            findViewById<RadioButton>(R.id.angry_whaleButton).setChecked(false)
            findViewById<RadioButton>(R.id.angry_violinButton).setChecked(false)
            findViewById<RadioButton>(R.id.angry_busButton).setChecked(true)
        }

        if(settings.get("happy_music") == "Violins"){
            findViewById<RadioButton>(R.id.happy_whaleButton).setChecked(false)
            findViewById<RadioButton>(R.id.happy_violinButton).setChecked(true)
            findViewById<RadioButton>(R.id.happy_busButton).setChecked(false)
        }
        else if(settings.get("happy_music") == "Whales"){
            findViewById<RadioButton>(R.id.happy_whaleButton).setChecked(true)
            findViewById<RadioButton>(R.id.happy_violinButton).setChecked(false)
            findViewById<RadioButton>(R.id.happy_busButton).setChecked(false)
        }
        else if(settings.get("happy_music") == "Bus"){
            findViewById<RadioButton>(R.id.happy_whaleButton).setChecked(false)
            findViewById<RadioButton>(R.id.happy_violinButton).setChecked(false)
            findViewById<RadioButton>(R.id.happy_busButton).setChecked(true)
        }
    }
}