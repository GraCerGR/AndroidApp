package com.example.myapplication.scripts

/**
 *  Блок определенной переменной.
 *  Позволяет объявить переменную и установить ей значение.
 **/
class DefinedVariable : Block() {
    private var value: Int = 0
    private var name: String = ""
    private var inputValue: String = ""
    private var inputName: String = ""

    init {
        type = "DefinedVariable"
    }

    override fun initVar() {
        inputName = inputLeftEdit
        inputValue = inputRightEdit
    }

    override fun executeBlock() {
        super.executeBlock()
        // Инициализируем поля из ввода
        initVar()

        // Отлавливаем неправильное название
        if (!variableCheck(inputName)) {
            errorType = incorrectNaming(inputName)
            return
        }


        // Высчитываем значение для переменной
        val calculated = arithmetics(mem, inputValue)
        errorType = calculated.first
        name = inputName
        // Присваиваем переменной значение
        if (calculated.first == ok()) {
            value = calculated.second
            mem.setVariableValue(name, value)
        }
    }
}