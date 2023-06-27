package com.badvibes.taskapp.domain.usecases

import com.badvibes.taskapp.domain.repo.TaskRepo
import com.badvibes.taskapp.domain.model.InvalidTaskException
import com.badvibes.taskapp.domain.model.Task
import com.google.android.material.snackbar.Snackbar

class AddTask(
    private val repo: TaskRepo
) {
    suspend fun execute(task: Task) {
        if (task.name.isBlank()) {
            return;
        }

        repo.insertTask(task)
    }
}