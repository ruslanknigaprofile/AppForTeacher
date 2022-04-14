package com.example.appforteacher.Presentation.View.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appforteacher.Domain.ViewModel.ViewModel
import com.example.appforteacher.R
import com.example.appforteacher.Task.Task

class TaskListAdapter(val taskList: ArrayList<Task>, val clickImage: ClickImage, val vm: ViewModel): RecyclerView.Adapter<TaskListAdapter.TaskHolder>() {

    class TaskHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val quest = itemView.findViewById<TextView>(R.id.quest)
        val type = itemView.findViewById<TextView>(R.id.type)
        val removeImage = itemView.findViewById<ImageView>(R.id.deleteTask)
    }

    interface ClickImage{
        fun clickImage(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskHolder(view)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.quest.text ="Задание: " + taskList[position].bodyTask
        if(taskList[position].typeTask == "Test"){
            holder.type.text = "Тип задания: Тест"
        } else if(taskList[position].typeTask == "Answer"){
            holder.type.text = "Тип задания: Ответ"
        }

        holder.itemView.setOnClickListener {
            vm.moreInfo(holder.position)
            if (taskList.get(holder.position).typeTask == "Test"){
                vm.replace("")
            }else if(taskList.get(holder.position).typeTask == "Answer"){
                vm.replace("AnswerTaskInfoFragment")
            }
        }

        holder.removeImage.setOnClickListener {
            clickImage.clickImage(holder.position)
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }
}