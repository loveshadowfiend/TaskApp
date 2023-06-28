package com.badvibes.taskapp.domain.usecases

import com.badvibes.taskapp.domain.repo.TaskRepo
import com.badvibes.taskapp.data.model.Task
import kotlinx.coroutines.flow.Flow

class GetUncompletedTasks(
    private val repo: TaskRepo
) {
    fun execute() : Flow<List<Task>> {
        return repo.getUncompletedTasks()
    }
}