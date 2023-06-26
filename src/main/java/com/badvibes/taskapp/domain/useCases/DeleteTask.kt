package com.badvibes.taskapp.domain.useCases

import com.badvibes.taskapp.domain.TaskRepo
import com.badvibes.taskapp.domain.model.Task

class DeleteTask(
    private val repo: TaskRepo
) {
    suspend operator fun invoke(task: Task) {
        repo.deleteTask(task)
    }
}