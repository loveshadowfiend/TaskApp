package com.badvibes.taskapp.presentation.components

import com.badvibes.taskapp.domain.model.Task

interface TaskClickListener {
    fun editTask(task: Task)
    fun completeTask(task: Task)
}