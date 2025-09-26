package com.example.cheermum

import android.os.Bundle
import android.widget.Button
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.home)


    }
    override fun onResume(){
        super.onResume()
        setContentView(R.layout.home)

        //Move to wifi config activity
        findViewById<Button>(R.id.wifi_button).setOnClickListener{
            val intent = Intent(this@MainActivity, WifiConfigActivity::class.java)
            startActivity(intent)
        }
        //Move to suggestion config activity
        findViewById<Button>(R.id.suggestion_button).setOnClickListener {
            val intent = Intent(this@MainActivity, SuggestionConfigActivity::class.java)
            startActivity(intent)
        }
    }
}