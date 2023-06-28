package com.badvibes.taskapp.data.datasource

import androidx.room.*
import com.badvibes.taskapp.data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task_table ORDER BY id ASC")
    fun getTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task_table WHERE completedDateString IS NULL")
    fun getUncompletedTasks() : Flow<List<Task>>

    @Query("SELECT * FROM task_table WHERE completedDateString IS NOT NULL")
    fun getCompletedTasks() : Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}