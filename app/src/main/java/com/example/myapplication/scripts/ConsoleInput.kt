package com.example.myapplication.scripts

import android.app.AlertDialog
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import com.example.myapplication.R

/**
 *  Блок одиночного консольного ввода.
 *  Позволяет вывести в консоль сообщение и считать введеное значение, присвоить его переменной.
 **/
class ConsoleInput : Block() {
    private var message: String = ""
    private var name: String = ""
    private var value: Int = 0
    private var nextB: Block? = null

    init {
        type = "ConsoleInput"
    }

    override fun initVar() {
        message = inputLeftEdit
        name = inputRightEdit
        nextB = nextBlock
    }

    override fun executeBlock() {
        super.executeBlock()
        // Инициализируем поля из ввода
        initVar()
        // Создаем штуки для диалоговоро окна
        val builder = AlertDialog.Builder(activity)
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_entry, null)
        val dialog = builder.create()

        // Устанавливаеи параметры диалогового окна
        dialog.setTitle(titleInput)
        dialog.setMessage(message)
        dialog.setView(view)
        // Создаем кнопку принятия данных
        val buttonEdit = view.findViewById<Button>(R.id.buttonEditDialog)
        val editVar = view.findViewById<EditText>(R.id.editVarDialog)

        // Привязываем кнопке нажатие
        buttonEdit.setOnClickListener {
            if (editVar.text.toString() != "") {
                valueVar = editVar.text.toString()
                dialog.dismiss()
                runConsoleInput()
            }
        }
        isProgramRunning = false
        dialog.show()
    }

    private fun inputAssignment() {
        // Делаем список из входных данных "1, arr[10], 3+1, 4-a" => ["1", "arr[10]", "3+1", "4-a"]
        val stringList = stringToList(valueVar)
        // Определяем, что за объект, которому мы будем присваивать
        val obj = defineInput(mem, name)

        // Если введена последовательность более чем из одного элемента
        if (stringList.size > 1) {
            // Отлавливаем попытку присвоить переменной последовательность
            if (obj.first == tagVariable()) {
                errorType = variableAssignSequence()
                return
            }

            return
        }
        // Если введено одно число
        // Высчитываем, что будем присваивать
        val calculated = arithmetics(mem, valueVar)
        errorType = calculated.first
        if (calculated.first != ok()) return
        if (obj.first !in listOf(tagArray(), tagVariable())) {
            errorType = obj.first
            return
        }
        value = calculated.second
        // Присваеваем высчитанное значение либо переменной, либо элементу массива
        when (obj.first) {
            tagVariable() -> {
                mem.setVariableValue(name, value)
            }
        }
    }

    private fun runConsoleInput() {
        // Если пользователь не отменил ввод, запускаем присваивание
        inputAssignment()
        when {
            nextB == null -> {
                isProgramRunning = false
                activity?.disconnectAllBlocks()
                adapterConsole.addMessage(programFinish(errorType))
                adapterBlocks.notifyItemChanged(indexListBlocks)
            }
            errorType == ok() -> {
                callStack.push(nextB)
            }
            else -> {
                isProgramRunning = false
                activity?.disconnectAllBlocks()
                adapterConsole.addMessage(programFinish(errorType))
                adapterBlocks.notifyItemChanged(indexListBlocks)
            }
        }
        isProgramRunning = true
    }
}
