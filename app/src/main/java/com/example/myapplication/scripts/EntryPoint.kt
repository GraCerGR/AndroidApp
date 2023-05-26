package com.example.myapplication.scripts

import android.content.Context

/**
 *  Точка входа в программу.
 **/
class EntryPoint : Block() {
    init {
        type = "EntryPoint"
    }

    override fun executeBlock() {
        // Очищаем все с прошлых запусков, вывадим сообщение о старте
        adapterConsole.addMessage(programStart())
        mem.clearVariables()

    }

    override fun kickRunning() {
        super.kickRunning()
        // Пока поднят флаг работы и стак непустой, выполняем крайний блок
        if (!isProgramRunning) return
        if (callStack.empty()) return
        callStack.pop().run()
    }
}