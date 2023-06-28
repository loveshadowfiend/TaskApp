package com.badvibes.taskapp.domain.usecases

import com.badvibes.taskapp.databinding.FragmentNewTaskSheetBinding
import com.badvibes.taskapp.domain.repo.TaskRepo
import com.badvibes.taskapp.data.model.Task
import java.time.LocalTime

class UpdateTask(
    private val repo: TaskRepo
) {
    suspend fun execute(task: Task, binding: FragmentNewTaskSheetBinding, dueTime: LocalTime?) {
        val name = binding.name.text.toString()
        if (name.isBlank()) return
        val desc = binding.name.text.toString()
        val dueTimeString = if (dueTime == null) null else Task.timeFormatter.format(dueTime)

        task.name = name
        task.desc = desc
        task.dueTimeString = dueTimeString

        repo.updateTask(task)
    }
}