package com.example.myapplication

import java.util.Collections


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ActivityMenuBinding

import com.example.myapplication.scripts.Assigneing
import com.example.myapplication.scripts.Begin
import com.example.myapplication.scripts.Block
import com.example.myapplication.scripts.ConditionIf
import com.example.myapplication.scripts.ConsoleOutput
import com.example.myapplication.scripts.DefinedVariable
import com.example.myapplication.scripts.End
import com.example.myapplication.scripts.EntryPoint
import com.example.myapplication.scripts.Exit
import com.example.myapplication.scripts.connectBlocks
import com.example.myapplication.scripts.ok
import com.example.myapplication.scripts.programFinish


class Main : AppCompatActivity() {

    private lateinit var container: RecyclerView
    //private lateinit var adapter: UniversalAdapter
    lateinit var bindingClass: ActivityMainBinding
    lateinit var beginningText: TextView
    private lateinit var listBlocks : ArrayList<Block>
    private lateinit var listMessage : ArrayList<String>
    private lateinit var blocksHandler : BlocksHandler
    lateinit var consoleAdapter : ConsoleHandler
    lateinit var mainHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        /*
        beginningText = findViewById(R.id.BeginningText) // Инициализация beginningText

        container = findViewById<RecyclerView>(R.id.blocksRV)
        container.layoutManager = LinearLayoutManager(this)

        adapter = UniversalAdapter()
        adapter.setBeginningText(beginningText) // Передаем ссылку на beginningText в адаптер
        container.adapter = adapter

        adapter.updateBeginningTextVisibility()

        val navigationView = findViewById<com.google.android.material.navigation.NavigationView>(R.id.navigationView)
        val drawerLayout = findViewById<androidx.drawerlayout.widget.DrawerLayout>(R.id.drawer) // Получение ссылки на DrawerLayout

        val assignmentButton = findViewById<Button>(R.id.Assignment)
        val definitionButton = findViewById<Button>(R.id.Definition)
        val ifButton = findViewById<Button>(R.id.If)
        val beginButton = findViewById<Button>(R.id.Begin)
        val endButton = findViewById<Button>(R.id.End)
        val outputButton = findViewById<Button>(R.id.Output)
*/
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "RUUUUUUUUUUUUUUUUN"
        bindingClass.blocksRV.isDrawingCacheEnabled = true
        bindingClass.blocksRV.setItemViewCacheSize(100)

        createConsoleView()
        createBlocksView()

        init()
        mainHandler = Handler(Looper.getMainLooper())

    }



    private val updateProgramRunning = object : Runnable {
        override fun run() {
            kicker()
            mainHandler.postDelayed(this, 1)
        }
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
            Definition.setOnClickListener{
                createDefinedVar()
            }
            Assignment.setOnClickListener{
                createAssignment()
            }
            If.setOnClickListener{
                createConditionIf()
            }
            Output.setOnClickListener{
                createConsoleOutput()
            }
            Calculate.setOnClickListener(){
                runProject(listBlocks)
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
        menuInflater.inflate(R.menu.project_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                val intent = Intent(this@Main, Menu::class.java)
                startActivity(intent)
            }
            R.id.menuRun -> {
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
            else if( type == "ConditionIf"){
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
    private fun createConsoleOutput() {
        bindingClass.blocksRV.scrollToPosition(listBlocks.size)
        blocksHandler.saveAllData()
        blocksHandler.addBlock(ConsoleOutput())
        listBlocks[listBlocks.size - 1].adapterConsole = consoleAdapter
        listBlocks[listBlocks.size - 1].adapterBlocks = blocksHandler
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
            listBlocks[i].activity = this

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

/*
// класс Adapter для RecyclerView
class UniversalAdapter : ListAdapter<Any, UniversalAdapter.ViewHolder>(DIFF_CALLBACK), ItemTouchHelperAdapter {

    private val items = mutableListOf<Int>()
    private lateinit var beginningText: TextView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(viewType, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Здесь вы можете настроить отображение данных в каждом элементе списка (без данного блока Адаптер не создаётся)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return items[position]
    }

    fun addItem(layoutId: Int) {
        items.add(layoutId)
        notifyDataSetChanged()
        updateBeginningTextVisibility()
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        Collections.swap(items, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
        updateBeginningTextVisibility() // Обновляем видимость beginningText после удаления элемента
    }

    fun setBeginningText(textView: TextView) {
        beginningText = textView // Устанавливаем ссылку на beginningText
        updateBeginningTextVisibility() // Вызываем метод для установки видимости beginningText
    }

    fun updateBeginningTextVisibility() {
        if (items.isEmpty()) {
            beginningText.visibility = View.VISIBLE
        } else {
            beginningText.visibility = View.GONE
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

class SimpleItemTouchHelperCallback(adapter: ItemTouchHelperAdapter) : ItemTouchHelper.Callback() {
    private val mAdapter: ItemTouchHelperAdapter

    init {
        mAdapter = adapter
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: ViewHolder, target: ViewHolder): Boolean {
        mAdapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
        mAdapter.onItemDismiss(viewHolder.adapterPosition)
    }
}

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)
    fun onItemDismiss(position: Int)
}
//являются ли oldItem и newItem одним и тем же объектом
private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        // являются ли oldItem и newItem одним и тем же объектом
        return oldItem === newItem
    }
    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        // Возвращаем true, чтобы указать, что содержимое элементов всегда считается одинаковым (без данного класса не создаётся)
        return true
    }


}
 */