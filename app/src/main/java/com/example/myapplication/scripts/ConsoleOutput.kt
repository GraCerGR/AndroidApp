package com.example.myapplication.scripts

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

// block for showing messages in the console
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

        // output the message if no expression
        if (expression == "") {
            adapterConsole.addMessage(message)
            return
        }

        // check the expression
        val calculated = arithmetics(mem, expression)
        errorType = calculated.first
        if (calculated.first != ok()) return
        adapterConsole.addMessage("$message${calculated.second}")
    }
}