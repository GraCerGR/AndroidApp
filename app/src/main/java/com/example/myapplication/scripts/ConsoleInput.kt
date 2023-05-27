package com.example.myapplication.scripts

import android.app.AlertDialog
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import com.example.myapplication.R

// block for asking user for input
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
        initVar()

        // show the input dialog
        val builder = AlertDialog.Builder(activity)
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_entry, null)
        val dialog = builder.create()
        dialog.setTitle(titleInput)
        dialog.setMessage(message)
        dialog.setView(view)
        val buttonEdit = view.findViewById<Button>(R.id.buttonEditDialog)
        val editVar = view.findViewById<EditText>(R.id.editVarDialog)

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
        val obj = defineInput(mem, name)


        // calculate the expression
        val calculated = arithmetics(mem, valueVar)
        errorType = calculated.first
        if (calculated.first != ok()) return
        if (obj.first !in listOf(tagArray(), tagVariable())) {
            errorType = obj.first
            return
        }
        value = calculated.second
        mem.setVariableValue(name, value)
    }

    private fun runConsoleInput() {
        inputAssignment()
        when {
            // if the input is empty
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
