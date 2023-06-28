package com.badvibes.taskapp.presentation.components

import com.badvibes.taskapp.data.model.Task

interface TaskClickListener {
    fun editTask(task: Task)
    fun completeTask(task: Task)
}