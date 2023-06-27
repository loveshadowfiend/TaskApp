package com.badvibes.taskapp.presentation.components

import androidx.lifecycle.*
import com.badvibes.taskapp.domain.repo.TaskRepo
import com.badvibes.taskapp.domain.model.Task
import com.badvibes.taskapp.domain.usecases.*
import kotlinx.coroutines.launch

class TaskViewModel(private val repo: TaskRepo): ViewModel() {
    // use cases
    private val addTask = AddTask(repo)
    private val getTasks = GetTasks(repo)
    private val updateTask = UpdateTask(repo)
    private val setCompleted = SetCompleted(repo)
    private val deleteTask = DeleteTask(repo)

    var tasks : LiveData<List<Task>> = getTasks.execute().asLiveData()

    fun addTask(newTask: Task) = viewModelScope.launch {
        addTask.execute(newTask)
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        updateTask.execute(task)
    }

    fun setCompleted(task: Task) = viewModelScope.launch {
        setCompleted.execute(task)
    }

    fun deleteTask(task: Task) = viewModelScope.launch {
        deleteTask.execute(task)
    }
}

class TaskModelFactory(
    private val repo: TaskRepo
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java))
            return TaskViewModel(repo) as T

        throw java.lang.IllegalArgumentException("unknown class")
    }
}