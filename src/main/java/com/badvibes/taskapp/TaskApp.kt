package com.badvibes.taskapp

import android.app.Application
import com.badvibes.taskapp.data.datasource.TaskDatabase
import com.badvibes.taskapp.data.repo.TaskRepoImpl

class TaskApp: Application() {
    private val database by lazy { TaskDatabase.getDatabase(this) }
    val repo by lazy { TaskRepoImpl(database.taskDao()) }
}