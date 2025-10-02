package com.example.cheermum

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import org.json.JSONObject


class WifiConfigActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.wifi_config )
    }
    override fun onResume(){
        super.onResume()
        setContentView(R.layout.wifi_config)

        //Information!
        val uuid = findViewById<EditText>(R.id.WifiName).text.toString()
        val password = findViewById<EditText>(R.id.WifiPassword).text.toString()

        //send data button
        findViewById<Button>(R.id.Submit_wifi).setOnClickListener {
            val wifiInfo = mapOf("uuid" to uuid, "password" to password)//Send this to pi somehow

            val json = JSONObject(wifiInfo)
        }

        //Go home button
        findViewById<Button>(R.id.Go_home).setOnClickListener {
            finish()
        }
    }
}