package com.example.myapplication

import android.annotation.SuppressLint
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


class Main : AppCompatActivity() {

    private lateinit var container: RecyclerView
    private lateinit var adapter: UniversalAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        container = findViewById<RecyclerView>(R.id.DefinitionRecyclerView)
        container.layoutManager = LinearLayoutManager(this)

        adapter = UniversalAdapter(R.layout.definition_block1) // Создание экземпляра адаптера
        container.adapter = adapter
        val navigationView = findViewById<com.google.android.material.navigation.NavigationView>(R.id.navigationView)
        val drawerLayout = findViewById<androidx.drawerlayout.widget.DrawerLayout>(R.id.drawerLayout) // Получение ссылки на DrawerLayout

        val assignmentButton = findViewById<Button>(R.id.Assignment)
        val definitionButton = findViewById<Button>(R.id.Definition)
        val ifButton = findViewById<Button>(R.id.If)
        val beginButton = findViewById<Button>(R.id.Begin)
        val endButton = findViewById<Button>(R.id.End)

        assignmentButton.setOnClickListener {
            // Устанавливаем layoutId для адаптера как R.layout.assignment_block1
            adapter.setLayoutId(R.layout.assignment_block1)
            adapter.addItem()
            drawerLayout.closeDrawer(navigationView) // Закрытие NavigationView
        }

        definitionButton.setOnClickListener {
            // Устанавливаем layoutId для адаптера
            adapter.setLayoutId(R.layout.definition_block1)
            adapter.addItem()
            drawerLayout.closeDrawer(navigationView) // Закрытие NavigationView
        }

        ifButton.setOnClickListener {
            // Устанавливаем layoutId для адаптера
            adapter.setLayoutId(R.layout.if_block1)
            adapter.addItem()
            drawerLayout.closeDrawer(navigationView) // Закрытие NavigationView
        }

        beginButton.setOnClickListener {
            // Устанавливаем layoutId для адаптера как R.layout.assignment_block1
            adapter.setLayoutId(R.layout.begin_block1)
            adapter.addItem()
            drawerLayout.closeDrawer(navigationView) // Закрытие NavigationView
        }

        endButton.setOnClickListener {
            // Устанавливаем layoutId для адаптера как R.layout.assignment_block1
            adapter.setLayoutId(R.layout.end_block1)
            adapter.addItem()
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

// Создайте класс Adapter для RecyclerView
class UniversalAdapter(private var layoutId: Int) : RecyclerView.Adapter<UniversalAdapter.ViewHolder>(),
    ItemTouchHelperAdapter {

    private val items = mutableListOf<Any>()
 //ioiojijo;ij
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(layoutId, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Здесь вы можете настроить отображение данных в каждом элементе списка
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem() {
        // Добавление нового элемента в список
        items.add(Any()) // Здесь вы можете использовать свои собственные данные
        notifyItemInserted(items.size - 1)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        // Перемещение элемента в списке
        val item = items.removeAt(fromPosition)
        items.add(toPosition, item)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        // Удаление элемента из списка
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun setLayoutId(newLayoutId: Int) {
        layoutId = newLayoutId
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

class SimpleItemTouchHelperCallback(adapter: ItemTouchHelperAdapter) :
    ItemTouchHelper.Callback() {
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
    override fun onMove(
        recyclerView: RecyclerView, viewHolder: ViewHolder,
        target: ViewHolder
    ): Boolean {
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

