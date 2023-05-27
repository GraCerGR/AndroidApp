package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ConsoleHandler(private val listOutputMessage: ArrayList<String>) :
    RecyclerView.Adapter<ConsoleHandler.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textMessage: TextView = itemView.findViewById(R.id.textMessage)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.console_bar, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textMessage.text = listOutputMessage[position]
    }

    override fun getItemCount(): Int {
        return listOutputMessage.size
    }

    fun addMessage(message: String) {
        listOutputMessage.add(message)
        notifyItemInserted(listOutputMessage.size - 1)
    }

    // clearing the messages of the last run
    fun clearListMessages() {
        val size = listOutputMessage.size
        if (size > 0) {
            for (i in 0 until size)
                listOutputMessage.removeAt(0)

            notifyItemRangeRemoved(0, size)
        }
    }
}