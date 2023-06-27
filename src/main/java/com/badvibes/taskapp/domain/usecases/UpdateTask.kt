package com.badvibes.taskapp.domain.usecases

import com.badvibes.taskapp.domain.repo.TaskRepo
import com.badvibes.taskapp.domain.model.InvalidTaskException
import com.badvibes.taskapp.domain.model.Task

class UpdateTask(
    private val repo: TaskRepo
) {
    suspend fun execute(task: Task) {
        if (task.name.isBlank()) {
            throw InvalidTaskException("The task name can't be empty")
        }

        repo.updateTask(task)
    }
}