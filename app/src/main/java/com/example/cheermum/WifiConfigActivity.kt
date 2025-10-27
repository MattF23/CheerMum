package com.example.cheermum

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import org.json.JSONObject
import java.io.File


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
        val ip = findViewById<EditText>(R.id.WifiName).text.toString()

        //save data button
        findViewById<Button>(R.id.Submit_wifi).setOnClickListener {
            val settings = JSONObject(mapOf("ip" to ip))

            val file = File(filesDir, "ip.json")
            file.writeText(settings.toString())
        }

        //Go home button
        findViewById<Button>(R.id.Go_home).setOnClickListener {
            finish()
        }
    }
}