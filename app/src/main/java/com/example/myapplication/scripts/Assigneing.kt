package com.example.myapplication.scripts

// block for assigning values to variables
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

        val obj = defineInput(mem, inputName)
        name = obj.second
        // check the initialization
        if (obj.first !in tagVariable()) {
            errorType = obj.first
            return
        }
        // calculate the expressions
        val calculated = arithmetics(mem, inputValue)
        errorType = calculated.first
        if (calculated.first != ok()) return
        value = calculated.second
        // assign the value
        when (obj.first) {
            tagVariable() -> {
                mem.setVariableValue(name, value)
            }
        }
    }
}