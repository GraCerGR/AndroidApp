package com.example.myapplication.scripts

import android.content.Context

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
        // Определяем, что за объект, которому мы будем присваивать
        val obj = defineInput(mem, inputName)
        name = obj.second
        // Отсеиваем ненужное
        if (obj.first !in listOf(tagArray(), tagVariable())) {
            errorType = obj.first
            return
        }
        // Высчитываем, что будем присваивать
        val calculated = arithmetics(mem, inputValue)
        errorType = calculated.first
        if (calculated.first != ok()) return
        value = calculated.second
        // Присваеваем высчитанное значение либо переменной, либо элементу массива
        when (obj.first) {
            tagVariable() -> {
                mem.setVariableValue(name, value)
            }
        }
    }
}