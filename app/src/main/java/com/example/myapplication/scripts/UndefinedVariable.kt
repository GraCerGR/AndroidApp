package com.example.myapplication.scripts

/**
 *  Блок неопределенной переменной.
 *  Позволяет объявить переменные со значением по умолчанию (0).
 **/
class UndefinedVariable : Block() {
    private var inputNames: List<String> = listOf()

    init {
        type = "UndefinedVariable"
    }

    override fun initVar() {
        // Делаем список из входных данных "a, B, Pussycat_17" => ["a", "B", "Pussycat_17"]
        inputNames = stringToList(inputLeftEdit)
    }

    override fun executeBlock() {
        super.executeBlock()
        // Инициализируем поля из ввода
        initVar()
        // Проверяем все переменные на правильность
        var flag = true
        for (el in inputNames) {
            // Отлавливаем неправильное название
            if (!variableCheck(el)) {
                errorType = incorrectNaming(el)
                flag = false
            }

        }
        // Создаем переменные, наполненные нулями
        if (flag) mem.createDefaultVariables(inputNames)
    }
}