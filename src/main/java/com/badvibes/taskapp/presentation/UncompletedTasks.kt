package com.badvibes.taskapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.badvibes.taskapp.R
import com.badvibes.taskapp.TaskApp
import com.badvibes.taskapp.data.model.Task
import com.badvibes.taskapp.databinding.FragmentNewTaskSheetBinding
import com.badvibes.taskapp.databinding.FragmentUncompletedTasksBinding
import com.badvibes.taskapp.presentation.components.TaskAdapter
import com.badvibes.taskapp.presentation.components.TaskClickListener
import com.badvibes.taskapp.presentation.components.TaskModelFactory
import com.badvibes.taskapp.presentation.components.TaskViewModel

class UncompletedTasks : Fragment(), TaskClickListener {
    private lateinit var binding: FragmentUncompletedTasksBinding
    private val taskViewModel: TaskViewModel by viewModels {
        TaskModelFactory((requireActivity().application as TaskApp).repo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setRecyclerView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUncompletedTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setRecyclerView() {
        val fragment = this
        taskViewModel.uncompletedTasks.observe(this) {
            binding.taskListRecylerView.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = TaskAdapter(it, fragment)
            }
        }
    }

    override fun editTask(task: Task) {
        NewTaskSheet(task).show(parentFragmentManager, "newTaskTag")
    }

    override fun completeTask(task: Task) {
        taskViewModel.setCompleted(task)
    }
}