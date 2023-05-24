package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
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
        val editMedium : EditText = itemView.findViewById(R.id.editMedium)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(viewType, parent, false)
        return ViewHolder(view)
    }


    override fun getItemViewType(position: Int): Int {
        val viewType = when(listBlocks[position].type){
            "EntryPoint" -> R.layout.start_point_block
            //"UndefinedVariable" -> R.layout.undefined_block
            "Assignment" -> R.layout.assignment_block
            "ConditionIf" -> R.layout.if_block
            "ConsoleOutput" -> R.layout.console_output
            "ConsoleInput" -> R.layout.console_input
            "Begin" -> R.layout.begin_block
            "End" -> R.layout.end_block
            else -> R.layout.definition_block
        }
        return viewType;
    }

    override fun getItemCount(): Int {
        return listBlocks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listBlocks[position].holder = holder
        if (listBlocks[position].type == "EntryPoint" || listBlocks[position].type== "Begin" ||
            listBlocks[position].type == "End" || listBlocks[position].type == "Else"){
            return
        }
        holder.editMedium.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
//                listBlocks[position].inputMediumEdit = holder.editMedium.text.toString()
                holder.editMedium.clearFocus()
            }
            false
        }
        holder.editLeft.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
//                listBlocks[position].inputLeftEdit = holder.editLeft.text.toString()
                holder.editLeft.clearFocus()
            }
            false
        }
        holder.editRight.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
//                listBlocks[position].inputRightEdit = holder.editRight.text.toString()
                holder.editRight.clearFocus()
            }
            false
        }

        holder.textType.text = listBlocks[position].type
        holder.textStatus.text = listBlocks[position].errorType
        holder.editLeft.setText(listBlocks[position].inputLeftEdit)
        holder.editMedium.setText(listBlocks[position].inputMediumEdit)
        holder.spinnerComparator.setSelection(listBlocks[position].indexComparator)
        holder.editRight.setText(listBlocks[position].inputRightEdit)
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