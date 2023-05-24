package com.example.myapplication

import java.util.Collections


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ActivityMenuBinding


class Main : AppCompatActivity() {

    //private lateinit var container: RecyclerView
    //private lateinit var adapter: UniversalAdapter
    lateinit var bindingClass: ActivityMenuBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
        supportActionBar?.title = ""
        init()
    }
    private fun init(){
        bindingClass.apply{
            BeginProgram.setOnClickListener{
                val intent = Intent(this@Main, ActivityHandler::class.java)
                startActivity(intent)
            }
            /*
            Settings.setOnClickListener{
                val intent = Intent(this@Main, Settings::class.java)
                startActivity(intent)
            }*/

            exit.setOnClickListener{
                finishAffinity()
            }
        }
    }
}
/*
        setContentView(R.layout.activity_main)

        container = findViewById<RecyclerView>(R.id.DefinitionRecyclerView)
        container.layoutManager = LinearLayoutManager(this)

        adapter = UniversalAdapter()
        container.adapter = adapter

        val navigationView = findViewById<com.google.android.material.navigation.NavigationView>(R.id.navigationView)
        val drawerLayout = findViewById<androidx.drawerlayout.widget.DrawerLayout>(R.id.drawerLayout) // Получение ссылки на DrawerLayout

        val assignmentButton = findViewById<Button>(R.id.Assignment)
        val definitionButton = findViewById<Button>(R.id.Definition)
        val ifButton = findViewById<Button>(R.id.If)
        val beginButton = findViewById<Button>(R.id.Begin)
        val endButton = findViewById<Button>(R.id.End)
        val outputButton = findViewById<Button>(R.id.Output)

        assignmentButton.setOnClickListener {
            adapter.addItem(R.layout.assignment_block1)
            drawerLayout.closeDrawer(navigationView) // Закрытие NavigationView
        }

        definitionButton.setOnClickListener {
            adapter.addItem(R.layout.definition_block1)
            drawerLayout.closeDrawer(navigationView) // Закрытие NavigationView
        }

        ifButton.setOnClickListener {
            adapter.addItem(R.layout.if_block1)
            adapter.addItem(R.layout.begin_block1)
            adapter.addItem(R.layout.end_block1)
            drawerLayout.closeDrawer(navigationView) // Закрытие NavigationView
        }

        beginButton.setOnClickListener {
            adapter.addItem(R.layout.begin_block1)
            drawerLayout.closeDrawer(navigationView) // Закрытие NavigationView
        }

        endButton.setOnClickListener {
            adapter.addItem(R.layout.end_block1)
            drawerLayout.closeDrawer(navigationView) // Закрытие NavigationView
        }

        outputButton.setOnClickListener {
            adapter.addItem(R.layout.output_block1)
            drawerLayout.closeDrawer(navigationView) // Закрытие NavigationView
        }

        val calculateButton = findViewById<Button>(R.id.Calculate)
        calculateButton.setOnClickListener {
            // Код для вычислений
        }

        val itemTouchHelper = ItemTouchHelper(SimpleItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(container)
    }
}

// класс Adapter для RecyclerView
class UniversalAdapter : ListAdapter<Any, UniversalAdapter.ViewHolder>(DIFF_CALLBACK), ItemTouchHelperAdapter {

    private val items = mutableListOf<Int>()

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
        notifyItemInserted(items.size - 1)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        Collections.swap(items, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
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
}*/