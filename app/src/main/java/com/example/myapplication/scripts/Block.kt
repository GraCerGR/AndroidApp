package com.example.myapplication.scripts


import com.example.myapplication.BlocksHandler
import java.util.Stack

open class Block {
    companion object {
        var callStack: Stack<Block> = Stack()
        var mem: Memory = Memory()
        var isProgramRunning = false
    }
    var inputLeftEdit: String = ""
    var inputRightEdit: String = ""
    var type: String = ""
    var errorType: String = ok()


    var indexListBlocks = 0
    lateinit var adapterBlocks: BlocksHandler
    lateinit var holder: BlocksHandler.ViewHolder
    var activity: ActivityHandler? = null


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
                    adapterBlocks.notifyItemChanged(indexListBlocks)
                }
                errorType == ok() -> {
                    callStack.push(nextBlock)
                }

                else -> {
                    isProgramRunning = false
                    adapterBlocks.notifyItemChanged(indexListBlocks)
                }
            }
        }
    }
}