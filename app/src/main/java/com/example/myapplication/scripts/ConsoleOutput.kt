package com.example.myapplication.scripts

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Main
import com.example.myapplication.databinding.ActivityMainBinding

class ConsoleOutput : Block() {
    private var message: String = ""
    private var expression: String = ""

    init {
        type = "ConsoleOutput"
    }

    override fun initVar() {
        message = if (inputLeftEdit == "") "" else "$inputLeftEdit "
        expression = inputRightEdit
    }

    override fun executeBlock() {
        super.executeBlock()
        initVar()
        val context: Context = AppCompatActivity()
        if (expression == "") {
            adapterConsole.addMessage(message)
            return
        }

        val calculated = arithmetics(mem, expression)
        errorType = calculated.first
        if (calculated.first != ok()) return
        adapterConsole.addMessage("$message${calculated.second}")
    }
}