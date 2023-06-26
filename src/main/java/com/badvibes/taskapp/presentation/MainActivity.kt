package com.badvibes.taskapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.badvibes.taskapp.TaskApp
import com.badvibes.taskapp.databinding.ActivityMainBinding
import com.badvibes.taskapp.domain.model.Task
import com.badvibes.taskapp.presentation.components.*

class MainActivity : AppCompatActivity(), TaskClickListener {
    private lateinit var binding: ActivityMainBinding
    private val taskViewModel: TaskViewModel by viewModels {
        TaskModelFactory((application as TaskApp).repo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newTaskButton.setOnClickListener{
            NewTaskSheet(null).show(supportFragmentManager, "newTaskTag")
        }

        setRecyclerView()
    }

    private fun setRecyclerView() {
        val mainActivity = this
        taskViewModel.tasks.observe(this) {
            binding.taskListRecylerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = TaskAdapter(it, mainActivity)
            }
        }
    }

    override fun editTask(task: Task) {
        NewTaskSheet(task).show(supportFragmentManager, "newTaskTag")
    }

    override fun completeTask(task: Task) {
        taskViewModel.setCompleted(task)
    }
}