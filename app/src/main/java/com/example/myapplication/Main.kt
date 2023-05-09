package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialog

class Main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       /* val bottomSheetFragment = BottomSheetFragment()
        bottomSheetFragment.*/

        val bottomSheetFragment = BottomSheetFragment()
/*        var button1 = findViewById<Button>(R.id.button1)
        button1.setOnClickListener{
            bottomSheetFragment.show(supportFragmentManager,"Bottom")
        }*/

       /* BottomSheetFragment.setOnClickListener {

            bottomSheetFragment.show(supportFragmentManager,"Bottom")
            *//*val setIntent = Intent(this, BottomSheetFragment::class.java)
            startActivity(setIntent)*//*
        }*/

    }
}