package com.badvibes.taskapp.data

import com.badvibes.taskapp.domain.TaskRepo
import com.badvibes.taskapp.domain.model.Task
import kotlinx.coroutines.flow.Flow

class TaskRepoImpl(
    private val dao: TaskDao
) : TaskRepo {
    override fun getTasks(): Flow<List<Task>> {
        return dao.getTasks()
    }

    override suspend fun getTaskById(id: Int) {
       dao.getTaskById(id)
    }

    override suspend fun insertTask(task: Task) {
        dao.insertTask(task)
    }

    override suspend fun updateTask(task: Task) {
        dao.updateTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        dao.deleteTask(task)
    }
}