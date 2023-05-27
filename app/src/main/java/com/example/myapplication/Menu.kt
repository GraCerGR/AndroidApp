package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityMenuBinding

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
                val intent = Intent(this@Menu, Main::class.java)
                startActivity(intent)
            }
            SettingsButton.setOnClickListener {
                val intent = Intent(this@Menu, Settings::class.java)
                startActivity(intent)
            }
            exit.setOnClickListener {
                finishAffinity()
            }
        }
    }
}