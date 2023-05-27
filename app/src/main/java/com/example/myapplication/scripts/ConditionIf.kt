package com.example.myapplication.scripts

// if condetion block
class ConditionIf : Block() {
    private var expressionLeft: String = ""
    private var expressionRight: String = ""
    private var comparator: String = ">="

    init {
        type = "ConditionIf"
    }

    override fun initVar() {
        expressionLeft = inputLeftEdit
        expressionRight = inputRightEdit
        comparator = inputComparator

        begin.adapterConsole = adapterConsole
        end.adapterConsole = adapterConsole
        exit = Exit()
        exit.adapterConsole = adapterConsole

        begin.adapterBlocks = this.adapterBlocks
        end.adapterBlocks = this.adapterBlocks
        exit.adapterBlocks = this.adapterBlocks
    }

    override fun executeBlock() {
        super.executeBlock()
        initVar()

        // Соединяем блок конца при выполнении условия с выходом - блоком которым соединен if блок
        connectBlocks(end, exit, clear = false)

        // Соединяем выход с блоком, после if, если это не блок логики if
        nextBlock?.let {
            if (nextBlock != begin && nextBlock != exit && nextBlock != end && nextBlock != null)
                connectBlocks(exit, it, clear = false)
        }

        // Проверяем правильность операторов сравнения
        if (comparator !in allComparators) {
            errorType = invalidComparator()
            return
        }

        // Высчитываем левую и правую часть для сравнения
        val calculateLeft = arithmetics(mem, expressionLeft)
        val calculateRight = arithmetics(mem, expressionRight)
        // Проверяем правильность вычислений
        if ((calculateLeft.first != ok()) || (calculateRight.first != ok())) {
            errorType = if (calculateLeft.first == ok()) calculateRight.first else calculateLeft.first
            return
        }
        // Сравниваем
        if (expressionComparator(
                calculateLeft.second,
                calculateRight.second,
                comparator
            )
        ) {
            connectBlocks(this, begin, false)
        } else {
            connectBlocks(this, exit, false)
        }
    }
}