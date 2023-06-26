package com.badvibes.taskapp.presentation.components

import androidx.lifecycle.*
import com.badvibes.taskapp.domain.TaskRepo
import com.badvibes.taskapp.domain.model.Task
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class TaskViewModel(private val repo: TaskRepo): ViewModel() {
    var tasks : LiveData<List<Task>> = repo.getTasks().asLiveData()

    fun addTask(newTask: Task) = viewModelScope.launch {
        repo.insertTask(newTask)
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        repo.updateTask(task)
    }

    fun setCompleted(task: Task) = viewModelScope.launch {
        if (!task.isCompleted()) {
            task.completedDateString = Task.dateFormatter.format(LocalDate.now())
        } else {
            task.completedDateString = null
            task.dueTimeString = null
        }

        repo.updateTask(task)
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