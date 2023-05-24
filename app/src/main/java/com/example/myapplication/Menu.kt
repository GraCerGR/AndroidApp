package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.myapplication.databinding.ActivityMenuBinding
import com.example.myapplication.scripts.Exit

class Menu : AppCompatActivity() {
    lateinit var bindingClass: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingClass = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        supportActionBar?.title = ""
        init()
    }

    private fun init() {
        bindingClass.apply {
            BeginProgram.setOnClickListener {
                val intent = Intent(this@Menu, ActivityHandler::class.java)
                startActivity(intent)
            }
            Settings.setOnClickListener {
                val intent = Intent(this@Menu, Settings::class.java)
                startActivity(intent)
            }
            exit.setOnClickListener {
                finishAffinity()
            }
        }
    }
}
/*
        var buttonProgramming = findViewById<Button>(R.id.BeginProgram)
        buttonProgramming.setOnClickListener {
            val menuIntent = Intent(this, ActivityHandler::class.java)
            startActivity(menuIntent)
        }

        var buttonSettings = findViewById<Button>(R.id.Settings)
        buttonSettings.setOnClickListener {
            val settingsIntent = Intent(this, Settings::class.java)
            startActivity(settingsIntent)
        }
    }
*/