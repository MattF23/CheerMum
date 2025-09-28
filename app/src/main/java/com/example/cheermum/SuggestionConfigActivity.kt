package com.example.cheermum

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.google.android.material.materialswitch.MaterialSwitch
import android.widget.Toast


class SuggestionConfigActivity : ComponentActivity(){
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

        //Go home button
        findViewById<Button>(R.id.Go_home).setOnClickListener {
            finish()
        }
        //switches
        findViewById<MaterialSwitch>(R.id.switch_yoga).setOnCheckedChangeListener { _, isChecked ->
            suggestYogaValue = isChecked
            Toast.makeText(this, suggestYogaValue.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}