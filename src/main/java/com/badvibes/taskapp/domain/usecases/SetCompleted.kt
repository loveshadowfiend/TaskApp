package com.badvibes.taskapp.domain.usecases

import com.badvibes.taskapp.domain.repo.TaskRepo
import com.badvibes.taskapp.domain.model.Task
import java.time.LocalDate

class SetCompleted(
    private val repo: TaskRepo
) {
    suspend fun execute(task: Task) {
        if (!task.isCompleted()) {
            task.completedDateString = Task.dateFormatter.format(LocalDate.now())
        } else {
            task.completedDateString = null
            task.dueTimeString = null
        }

        repo.updateTask(task)
    }
}