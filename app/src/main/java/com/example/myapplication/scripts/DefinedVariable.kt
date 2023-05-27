package com.example.myapplication.scripts

import android.content.Context

// block for defining variables with or without a value
class DefinedVariable : Block() {
    private var value: Int = 0
    private var name: String = ""
    private var inputValue: String = ""
    private var inputName: String = ""

    init {
        type = "Defination"
    }

    override fun initVar() {
        inputName = inputLeftEdit
        inputValue = inputRightEdit
    }

    override fun executeBlock() {
        super.executeBlock()
        // add the name and value in the object
        initVar()

        // if the variable is not given a value
        if (inputValue == "") mem.createDefaultVariable(inputName)

        // checking if the name invalid
        if (!variableCheck(inputName)) {
            errorType = incorrectNaming(inputName)
            return
        }


        // calculating the value of the expression
        val calculated = arithmetics(mem, inputValue)
        errorType = calculated.first
        name = inputName

        // assign value to variable
        if (calculated.first == ok()) {
            value = calculated.second
            mem.setVariableValue(name, value)
        }
    }
}