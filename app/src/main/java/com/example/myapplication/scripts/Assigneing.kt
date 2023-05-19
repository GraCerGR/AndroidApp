package com.example.myapplication.scripts

class Assigneing : Block() {

    private var value: Int = 0
    private var name: String = ""
    private var inputValue: String = ""
    private var inputName: String = ""

    init {
        type = "Assignment"
    }

    override fun initVar() {
        inputName = inputLeftEdit
        inputValue = inputRightEdit
    }

    override fun executeBlock() {
        super.executeBlock()
        initVar()
        //TODO complete function
    }
}