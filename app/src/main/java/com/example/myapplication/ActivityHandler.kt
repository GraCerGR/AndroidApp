package com.example.myapplication

import android.content.Intent
import android.icu.util.Output
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.scripts.Assigneing
import com.example.myapplication.scripts.Begin
import com.example.myapplication.scripts.Block
import com.example.myapplication.scripts.ConditionIf
import com.example.myapplication.scripts.DefinedVariable
import com.example.myapplication.scripts.End
import com.example.myapplication.scripts.EntryPoint
import com.example.myapplication.scripts.Exit
import com.example.myapplication.scripts.connectBlocks
import com.example.myapplication.scripts.ok
import com.example.myapplication.scripts.programFinish
import com.example.myapplication.databinding.ActivityProjectBinding
import java.util.Collections

class ActivityHandler:AppCompatActivity(){
        private lateinit var listBlocks : ArrayList<Block>
        private lateinit var listMessage : ArrayList<String>
        private lateinit var blocksHandler : BlocksHandler
        lateinit var consoleAdapter : ConsoleHandler
        lateinit var bindingClass : ActivityProjectBinding
        lateinit var mainHandler: Handler
        private lateinit var menu: Menu

    private val updateProgramRunning = object : Runnable {
            override fun run() {
                kicker()
                mainHandler.postDelayed(this, 1)
            }
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            super.onCreate(savedInstanceState)
            bindingClass = ActivityProjectBinding.inflate(layoutInflater)
            setContentView(bindingClass.root)
            val inflater: MenuInflater = menuInflater
            inflater.inflate(R.menu.project_menu, menu)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "RUUUUUUUUUUUUUUUUN"
            bindingClass.blocksRV.isDrawingCacheEnabled = true
            bindingClass.blocksRV.setItemViewCacheSize(100)

            createConsoleView()
            createBlocksView()

            init()

            mainHandler = Handler(Looper.getMainLooper())
        }
        fun kicker() {
            listBlocks[0].kickRunning()
        }
        override fun onPause() {
            super.onPause()
            mainHandler.removeCallbacks(updateProgramRunning)
        }

        override fun onResume() {
            super.onResume()
            mainHandler.post(updateProgramRunning)
        }

        private fun init(){
            bindingClass.apply{
                buttonDefinedVar.setOnClickListener{
                    createDefinedVar()
                }
                buttonAssignment.setOnClickListener{
                    createAssignment()
                }
                buttonConditionIf.setOnClickListener{
                    createConditionIf()
                }
                buttonConsoleOutput.setOnClickListener{
                    createConsoleView()
                }
            }
        }

    private fun createConditionIf() {
        bindingClass.blocksRV.scrollToPosition(listBlocks.size + 2)
        blocksHandler.saveAllData()
        blocksHandler.addBlock(ConditionIf())
        blocksHandler.addBlock(Begin())
        blocksHandler.addBlock(End())
        listBlocks[listBlocks.size - 3].adapterConsole = consoleAdapter
        listBlocks[listBlocks.size - 3].adapterBlocks = blocksHandler
        listBlocks[listBlocks.size - 3].begin = Begin()
        listBlocks[listBlocks.size - 3].end = End()
        listBlocks[listBlocks.size - 3].exit = Exit()
        listBlocks[listBlocks.size - 2].adapterConsole = consoleAdapter
        listBlocks[listBlocks.size - 2].adapterBlocks = blocksHandler
        listBlocks[listBlocks.size - 1].adapterBlocks = blocksHandler
        listBlocks[listBlocks.size - 1].adapterConsole = consoleAdapter
    }


    private fun createDefinedVar() {
        bindingClass.blocksRV.scrollToPosition(listBlocks.size)
        blocksHandler.saveAllData()
        blocksHandler.addBlock(DefinedVariable())
        listBlocks[listBlocks.size-1].adapterConsole = consoleAdapter
        listBlocks[listBlocks.size-1].adapterBlocks = blocksHandler
    }

    private fun createAssignment() {
        bindingClass.blocksRV.scrollToPosition(listBlocks.size)
        blocksHandler.saveAllData()
        blocksHandler.addBlock(Assigneing())
        listBlocks[listBlocks.size - 1].adapterConsole = consoleAdapter
        listBlocks[listBlocks.size - 1].adapterBlocks = blocksHandler
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (menu != null) {
            this.menu = menu
        }
        menuInflater.inflate(R.menu.project_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                val intent = Intent(this@ActivityHandler, Menu::class.java)
                startActivity(intent)
            }
            R.id.menuRun -> {
                Toast.makeText(this, "Запуск...", Toast.LENGTH_SHORT).show()
                runProject(listBlocks)
            }
        }
        return true
    }

    private val simpleCallback = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN or
            ItemTouchHelper.START or ItemTouchHelper.END, ItemTouchHelper.LEFT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            if (viewHolder.adapterPosition == 0 || target.adapterPosition == 0 ){
                return true
            }
            val fromPosition = viewHolder.adapterPosition
            val toPosition = target.adapterPosition
            Collections.swap(listBlocks, fromPosition, toPosition)
            blocksHandler.notifyItemMoved(fromPosition, toPosition)
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position =  viewHolder.adapterPosition
            val type = listBlocks[position].type
            if (viewHolder.adapterPosition == 0 || type == "Begin" || type == "End" || type == "Else"){
                blocksHandler.notifyItemRemoved(position)
                return
            }
            else if( type == "ConditionIf" || type == "ConditionIfElse" || type == "CycleWhile"){
                listBlocks.remove(listBlocks[position])
                var positionBegin = findNearBegin(position)
                listBlocks.remove(listBlocks[positionBegin])
                blocksHandler.notifyItemRemoved(position)
                blocksHandler.notifyItemRemoved(positionBegin)
                var positionEnd = findNearEnd(position)
                listBlocks.remove(listBlocks[positionEnd])
                blocksHandler.notifyItemRemoved(positionEnd)
                return
            }
            listBlocks.remove(listBlocks[position])
            blocksHandler.notifyItemRemoved(position)
        }
    }
    private fun findNearBegin(start : Int) : Int{
        for (i in start until listBlocks.size)
            if (listBlocks[i].type == "Begin")
                return i
        for (i in 0 until start)
            if (listBlocks[i].type == "Begin")
                return i
        return -1
    }
    private fun findNearEnd(start : Int) : Int{
        for (i in start until listBlocks.size)
            if (listBlocks[i].type == "End")
                return i
        for (i in 0 until start)
            if (listBlocks[i].type == "End")
                return i
        return -1
    }

    private fun createBlocksView(){
        val blocksView : RecyclerView = findViewById(R.id.blocksRV)
        blocksView.layoutManager = LinearLayoutManager(this)
        listBlocks = setListBlocks()
        blocksHandler = BlocksHandler(listBlocks)
        blocksView.adapter = blocksHandler
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(blocksView)
    }
    private fun createConsoleView(){
        val consoleView : RecyclerView = bindingClass.messageRV
        consoleView.layoutManager = LinearLayoutManager(this)
        listMessage = setListMessage()
        consoleAdapter = ConsoleHandler(listMessage)
        consoleView.adapter = consoleAdapter
    }
    private fun setListBlocks() : ArrayList<Block>{
        val list = ArrayList<Block>()
        list.add(EntryPoint())
        list[0].adapterConsole = consoleAdapter
        return list
    }
    private fun setListMessage(): ArrayList<String> {
        return ArrayList()
    }

    private fun connectionInBlock(index : Int) : Pair<Int, Int>{
        var i = index
        while (listBlocks[i+1].type != "End"){
            if (i+1 == listBlocks.size) {
                consoleAdapter.addMessage("В строке $i ожидался End")
                return Pair(0, 0)
            }
            if (listBlocks[i].type == "ConditionIf" ||
                listBlocks[i].type == "ConditionIfElse" ||
                listBlocks[i].type == "CycleWhile"){
                val temp = if (listBlocks[i].type == "ConditionIfElse")
                    connectionIfElse(i)
                else
                    connectionIfOrWhile(i)

                if(temp == 0)
                    return Pair(0, 0)
                else {
                    if (temp < listBlocks.size && listBlocks[temp].type != "End"){
                        connectBlocks(listBlocks[i], listBlocks[temp])
                        i = temp
                    }
                    else if(temp < listBlocks.size && listBlocks[temp].type == "End"){
                        return Pair(i, temp)
                    }
                    else if(temp >= listBlocks.size){
                        consoleAdapter.addMessage("В строке $temp ожидался End")
                        return Pair(0, 0)
                    }
                    else
                        break
                }
            }
            else {
                connectBlocks(listBlocks[i], listBlocks[i+1])
                i += 1
            }
        }
        return Pair(i, 0)
    }

    private fun connectionIfElse (index : Int): Int{
        var i = index + 1
        if (listBlocks[i].type == "Begin"){
            i += 1
            if (listBlocks[i].type != "End"){
                connectBlocks(listBlocks[index].begin, listBlocks[i])
                val (j, temp) = connectionInBlock(i)
                if (j == 0)
                    return 0
                else if (listBlocks[temp].type == "End") {
                    connectBlocks(listBlocks[j], listBlocks[index].end)
                    i = temp + 1
                }
                else{
                    connectBlocks(listBlocks[j], listBlocks[index].end)
                    i = j + 2
                }
            }
            else{
                connectBlocks(listBlocks[index].begin, listBlocks[index].end)
                i += 1
            }
                consoleAdapter.addMessage("В строке $i ожидался Else")
                return 0
        }
        else {
            consoleAdapter.addMessage("В строке $i ожидался Begin")
            return 0
        }
    }

    private fun connectionIfOrWhile(index : Int): Int{
        var i = index + 1
        if (listBlocks[i].type == "Begin"){
            i += 1
            if (listBlocks[i].type == "End"){
                connectBlocks(listBlocks[index].begin, listBlocks[index].end)
                return i + 1
            }
            connectBlocks(listBlocks[index].begin, listBlocks[i])
            val (i, temp) = connectionInBlock(i)
            if (i == 0)
                return 0
            else if (listBlocks[temp].type == "End") {
                connectBlocks(listBlocks[i], listBlocks[index].end)
                return temp + 1
            }
            connectBlocks(listBlocks[i], listBlocks[index].end)
            return i + 2
        }
        else {
            consoleAdapter.addMessage("В строке $i ожидался Begin")
            return 0
        }
    }

    private fun connectionBlocks() : Boolean{
        var i = 0
        while (i < listBlocks.size-1) {
            if(listBlocks[i].type == "Begin"){
                consoleAdapter.addMessage("Обнаружен не к месту Begin в строке $i")
                return false
            }
            else if(listBlocks[i].type == "End"){
                consoleAdapter.addMessage("Обнаружен не к месту End в строке $i")
                return false
            }
            else if(listBlocks[i].type == "Else"){
                consoleAdapter.addMessage("Обнаружен не к месту Else в строке $i")
                return false
            }
            else if (listBlocks[i].type == "ConditionIf" ||
                listBlocks[i].type == "ConditionIfElse" ||
                listBlocks[i].type == "CycleWhile") {

                val temp = if (listBlocks[i].type == "ConditionIfElse")
                    connectionIfElse(i)
                else
                    connectionIfOrWhile(i)

                if (temp == 0) return false
                else {
                    if (temp < listBlocks.size){
                        connectBlocks(listBlocks[i], listBlocks[temp])
                        i = temp
                    }
                    else
                        break
                }
            }
            else{
                connectBlocks(listBlocks[i], listBlocks[i + 1])
                i += 1
            }
        }
        return true
    }

    private fun runProject(listBlocks : ArrayList<Block>){
        listBlocks[0].adapterBlocks = blocksHandler
        consoleAdapter.clearListMessages()
        blocksHandler.saveAllData()
        for (i in 0 until listBlocks.size){
            listBlocks[i].indexListBlocks = i
            listBlocks[i].flagInit = true
            if (listBlocks[i].errorType != ok()){
                listBlocks[i].errorType = ok()
                blocksHandler.notifyItemChanged(i)
            }
            //listBlocks[i].activity = this

        }
        if (!connectionBlocks()) {
            consoleAdapter.addMessage(programFinish("Fail"))
            return
        }
        Block.isProgramRunning = true
        listBlocks[0].run()
        bindingClass.drawer.openDrawer(GravityCompat.START)
    }

    private fun printAllConnections(){
        for (i in 0 until listBlocks.size){
            consoleAdapter.addMessage("$i "+ (listBlocks[i].prevBlock
                ?.type?: "null") + "-"+ listBlocks[i].type + "-" +
                    (listBlocks[i].nextBlock?.type?: "null"))

        }
    }
    fun disconnectAllBlocks(){
        for (i in 0 until listBlocks.size){
            listBlocks[i].prevBlock = null
            listBlocks[i].nextBlock = null
            listBlocks[i].begin = Begin()
            listBlocks[i].end = End()
            listBlocks[i].exit = Exit()
        }
    }
}