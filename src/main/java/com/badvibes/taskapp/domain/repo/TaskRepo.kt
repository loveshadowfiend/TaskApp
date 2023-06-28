package com.badvibes.taskapp.domain.repo

import com.badvibes.taskapp.data.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepo {
    fun getTasks(): Flow<List<Task>>

    fun getUncompletedTasks() : Flow<List<Task>>

    fun getCompletedTasks() : Flow<List<Task>>

    suspend fun insertTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun deleteTask(task: Task)
}