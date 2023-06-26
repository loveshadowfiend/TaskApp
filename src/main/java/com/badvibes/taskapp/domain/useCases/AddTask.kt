package com.badvibes.taskapp.domain.useCases

import com.badvibes.taskapp.domain.TaskRepo
import com.badvibes.taskapp.domain.model.InvalidTaskException
import com.badvibes.taskapp.domain.model.Task

class AddTask(
    private val repo: TaskRepo
) {
    suspend operator fun invoke(task: Task) {
        if (task.name.isBlank()) {
            throw InvalidTaskException("The task name can't be empty")
        }

        repo.insertTask(task)
    }
}