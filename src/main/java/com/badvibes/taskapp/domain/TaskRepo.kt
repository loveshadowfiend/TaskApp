package com.badvibes.taskapp.domain

import com.badvibes.taskapp.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepo {
    fun getTasks(): Flow<List<Task>>

    suspend fun getTaskById(id: Int)

    suspend fun insertTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun deleteTask(task: Task)
}