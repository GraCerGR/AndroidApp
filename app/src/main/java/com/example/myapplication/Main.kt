package com.example.myapplication

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout

class Main : AppCompatActivity() {

    private lateinit var inputEditText: EditText
    private lateinit var container: LinearLayout
    private var draggedView: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        container = findViewById<LinearLayout>(R.id.secondActivityContainer)
        var assignment = findViewById<Button>(R.id.Assignment)
        val navigationView = findViewById<com.google.android.material.navigation.NavigationView>(R.id.navigationView)
        assignment.setOnClickListener {

            val inflater = LayoutInflater.from(this)
            val layoutObject = inflater.inflate(R.layout.assignment_block1, container, false)
            container.addView(layoutObject)


            val drawerLayout = findViewById<androidx.drawerlayout.widget.DrawerLayout>(R.id.drawerLayout) // Получение ссылки на DrawerLayout
            drawerLayout.closeDrawer(navigationView) // Закрытие NavigationView

        }
        inputEditText = findViewById(R.id.editRight)

        var calculate = findViewById<Button>(R.id.Calculate)
        calculate.setOnClickListener {

            /*Код вычисления*/

        }
    }
}




