package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Menu : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        var buttonProgramming = findViewById<Button>(R.id.BeginProgram)
        buttonProgramming.setOnClickListener {
            val menuIntent = Intent(this, Main::class.java)
            startActivity(menuIntent)
        }

        var buttonSettings = findViewById<Button>(R.id.Settings)
        buttonSettings.setOnClickListener {
            val settingsIntent = Intent(this, Settings::class.java)
            startActivity(settingsIntent)
        }
    }
}