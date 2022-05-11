package com.example.todolistapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter (
    val context: Context,
    val taskClickChangeStatusInterface: taskClickChangeStatusInterface,
    val taskClickDeleteInterface: taskClickDeleteInterface) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    private val allTask = ArrayList<TaskModel>()

    inner class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        val name = itemView.findViewById<TextView>(R.id.nameText)
        val description = itemView.findViewById<TextView>(R.id.descriptionText)
        val deadline = itemView.findViewById<TextView>(R.id.deadlineText)
        val type = itemView.findViewById<TextView>(R.id.typeText)
        val status = itemView.findViewById<TextView>(R.id.statusText)
        val options = itemView.findViewById<ImageView>(R.id.options)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task_design,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.setText(allTask.get(position).name)
        holder.description.setText(allTask.get(position).description)
        holder.deadline.setText(allTask.get(position).deadline)
        holder.type.setText(allTask.get(position).type)
        holder.status.setText(allTask.get(position).status)
        holder.options.setOnClickListener {
            showPopUpMenu(it, position)
        }
    }

    private fun showPopUpMenu(view: View?, position: Int) {
        val task: TaskModel = allTask.get(position)
        val popupMenu = PopupMenu(context, view)
        popupMenu.menuInflater.inflate(R.menu.option_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener{
                item -> when(item.itemId){
            R.id.menuDelete ->
                taskClickDeleteInterface.onDeleteIconClick(allTask.get(position))
            R.id.menuAdvance ->
                taskClickChangeStatusInterface.onChangeStatusIconClick(allTask.get(position))
            }
            true
        }
        popupMenu.show()

    }

    override fun getItemCount(): Int {
        return allTask.size
    }

    fun updateList(newList: List<TaskModel>){
        allTask.clear()
        allTask.addAll(newList)
        notifyDataSetChanged()
    }
}

interface taskClickChangeStatusInterface {
    fun onChangeStatusIconClick(task: TaskModel)

}

interface taskClickDeleteInterface {
    fun onDeleteIconClick(task: TaskModel)
}
