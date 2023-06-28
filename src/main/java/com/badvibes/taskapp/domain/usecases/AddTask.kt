package com.badvibes.taskapp.domain.usecases

import com.badvibes.taskapp.databinding.FragmentNewTaskSheetBinding
import com.badvibes.taskapp.domain.repo.TaskRepo
import com.badvibes.taskapp.domain.model.Task
import java.time.LocalTime

class AddTask(
    private val repo: TaskRepo
) {
    suspend fun execute(binding: FragmentNewTaskSheetBinding, dueTime: LocalTime?) {
        val name = binding.name.text.toString()
        if (name.isBlank()) return
        val desc = binding.desc.text.toString()
        val dueTimeString = if (dueTime == null) null else Task.timeFormatter.format(dueTime)

        val newTask = Task(name, desc, dueTimeString, null)
        repo.insertTask(newTask)
    }
}