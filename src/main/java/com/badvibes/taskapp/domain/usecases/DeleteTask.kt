package com.badvibes.taskapp.domain.usecases

import com.badvibes.taskapp.domain.repo.TaskRepo
import com.badvibes.taskapp.domain.model.Task

class DeleteTask(
    private val repo: TaskRepo
) {
    suspend fun execute(task: Task) {
        repo.deleteTask(task)
    }
}