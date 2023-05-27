package com.example.myapplication.scripts


import com.example.myapplication.BlocksHandler
import com.example.myapplication.ConsoleHandler
import com.example.myapplication.Main
import java.util.Stack

// block class
open class Block() {
    companion object {
        var callStack: Stack<Block> = Stack()
        var mem: Memory = Memory()
        var isProgramRunning = false
    }
    var inputLeftEdit: String = ""
    var inputRightEdit: String = ""
    var inputMediumEdit: String = ""
    var inputComparator: String = ">="
    var indexComparator: Int = 0
    var valueVar: String = ""

    lateinit var begin: Begin
    lateinit var end: End
    lateinit var exit: Exit

    var type: String = ""
    var errorType: String = ok()


    var indexListBlocks = 0
    lateinit var adapterBlocks: BlocksHandler
    lateinit var holder: BlocksHandler.ViewHolder
    lateinit var adapterConsole: ConsoleHandler
    var activity: Main? = null

    var flagInit = true

    var nextBlock: Block? = null
    var prevBlock: Block? = null


    fun accessMemory(): Memory {
        return mem
    }


    open fun initVar() {
    }

    open fun executeBlock() {
        errorType = ok()
    }

    open fun kickRunning() {}

    open fun run() {
        if (type == "ConsoleInput") {
            executeBlock()
        } else {
            executeBlock()
            when {

                nextBlock == null -> {
                    isProgramRunning = false
                    activity?.disconnectAllBlocks()
                    adapterBlocks.notifyItemChanged(indexListBlocks)
                }
                errorType == ok() -> {
                    callStack.push(nextBlock)
                }

                else -> {
                    isProgramRunning = false
                    activity?.disconnectAllBlocks()
                    adapterBlocks.notifyItemChanged(indexListBlocks)
                }
            }
        }
    }
}