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
    private lateinit var adapter: AssignmentAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        container = findViewById<RecyclerView>(R.id.DefinitionRecyclerView)
        container.layoutManager = LinearLayoutManager(this)
        adapter = AssignmentAdapter()
        container.adapter = adapter
        val navigationView = findViewById<com.google.android.material.navigation.NavigationView>(R.id.navigationView)

        val assignmentButton = findViewById<Button>(R.id.Definition)

        assignmentButton.setOnClickListener {
            // Добавление нового элемента в список
            adapter.addAssignment()
            val drawerLayout = findViewById<androidx.drawerlayout.widget.DrawerLayout>(R.id.drawerLayout) // Получение ссылки на DrawerLayout
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
class AssignmentAdapter : RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder>(),
    ItemTouchHelperAdapter {

    private val assignments = mutableListOf<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignmentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.definition_block1, parent, false)
        return AssignmentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AssignmentViewHolder, position: Int) {
        // Здесь вы можете настроить отображение данных в каждом элементе списка
    }

    override fun getItemCount(): Int {
        return assignments.size
    }

    fun addAssignment() {
        // Добавление нового элемента в список
        assignments.add(Any()) // Здесь вы можете использовать свои собственные данные
        notifyItemInserted(assignments.size - 1)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        // Перемещение элемента в списке
        val assignment = assignments.removeAt(fromPosition)
        assignments.add(toPosition, assignment)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        // Удаление элемента из списка
        assignments.removeAt(position)
        notifyItemRemoved(position)
    }

    class AssignmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
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

