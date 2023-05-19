package com.example.myapplication

import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.scripts.Block

class BlocksHandler(private val listBlocks:ArrayList<Block>) : RecyclerView.Adapter<BlocksHandler.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textType: TextView = itemView.findViewById(R.id.textType)
        val textStatus: TextView = itemView.findViewById(R.id.textStatus)
        val spinnerComparator: Spinner = itemView.findViewById(R.id.spinnerComparator)
        val editLeft: EditText = itemView.findViewById(R.id.editLeft)
        val editRight: EditText = itemView.findViewById(R.id.editRight)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return listBlocks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    fun addBlock(block : Block){
        listBlocks.add(block)
        notifyItemInserted(listBlocks.size-1)
    }

    fun saveAllData(){
        for (i in 0 until listBlocks.size){
            listBlocks[i].inputLeftEdit =  listBlocks[i].holder.editLeft.text.toString()
            listBlocks[i].inputRightEdit =  listBlocks[i].holder.editRight.text.toString()
        }
    }
}