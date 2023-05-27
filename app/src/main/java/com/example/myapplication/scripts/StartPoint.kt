package com.example.myapplication.scripts


//the starting block for the program
class StartPoint : Block() {
    init {
        type = "EntryPoint"
    }

    override fun executeBlock() {
        //show start message in console and delete any left over variables

        adapterConsole.addMessage(programStart())
        mem.clearVariables()

    }

    override fun kickRunning() {
        super.kickRunning()
        // keep the staring block while the program is running
        if (!isProgramRunning) return
        if (callStack.empty()) return
        callStack.pop().run()
    }
}