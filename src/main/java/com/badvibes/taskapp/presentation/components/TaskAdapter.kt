package com.badvibes.taskapp.presentation.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.badvibes.taskapp.databinding.TaskCellBinding
import com.badvibes.taskapp.domain.model.Task

class TaskAdapter(
    private val tasks: List<Task>,
    private val clickListener: TaskClickListener
): RecyclerView.Adapter<TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = TaskCellBinding.inflate(from, parent, false)

        return TaskViewHolder(parent.context, binding, clickListener)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bindTask(tasks[position])
    }
}