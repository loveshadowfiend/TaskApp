package com.badvibes.taskapp.presentation.components

import androidx.lifecycle.*
import com.badvibes.taskapp.databinding.FragmentNewTaskSheetBinding
import com.badvibes.taskapp.domain.repo.TaskRepo
import com.badvibes.taskapp.domain.model.Task
import com.badvibes.taskapp.domain.usecases.*
import kotlinx.coroutines.launch
import java.time.LocalTime

class TaskViewModel(private val repo: TaskRepo): ViewModel() {
    // use cases
    private val addTask = AddTask(repo)
    private val getTasks = GetTasks(repo)
    private val updateTask = UpdateTask(repo)
    private val setCompleted = SetCompleted(repo)
    private val deleteTask = DeleteTask(repo)

    var tasks : LiveData<List<Task>> = getTasks.execute().asLiveData()

    fun addTask(binding: FragmentNewTaskSheetBinding, dueTime: LocalTime?) = viewModelScope.launch {
        addTask.execute(binding, dueTime)
    }

    fun updateTask(task: Task, binding: FragmentNewTaskSheetBinding, dueTime: LocalTime?) = viewModelScope.launch {
        updateTask.execute(task, binding, dueTime)
    }

    fun setCompleted(task: Task) = viewModelScope.launch {
        setCompleted.execute(task)
    }

    fun deleteTask(task: Task) = viewModelScope.launch {
        deleteTask.execute(task)
    }

    fun openTimePicker() = viewModelScope.launch {

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