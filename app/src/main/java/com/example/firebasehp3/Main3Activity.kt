package com.example.firebasehp3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Main3Activity : AppCompatActivity() {

    private var latiApp: String? = null
    private var langiApp: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        if (intent.extras != null){
            val terima = intent.extras
            latiApp = terima?.getString("langiApp")
            langiApp = terima?.getString("latiApp")
        }else{
            latiApp = "0.0"
            langiApp = "0.0"
        }
        var textkuy = findViewById<TextView>(R.id.textView)
        textkuy.text = latiApp+langiApp
    }
}
