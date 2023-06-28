package com.badvibes.taskapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.badvibes.taskapp.TaskApp
import com.badvibes.taskapp.databinding.ActivityMainBinding
import com.badvibes.taskapp.data.model.Task
import com.badvibes.taskapp.presentation.components.*
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity(), TaskClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ViewPagerAdapter
    private val taskViewModel: TaskViewModel by viewModels {
        TaskModelFactory((application as TaskApp).repo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Uncompleted Tasks"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Completed Tasks"))

        binding.viewPager.adapter = adapter

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    binding.viewPager.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })

        binding.newTaskButton.setOnClickListener{
            NewTaskSheet(null).show(supportFragmentManager, "newTaskTag")
        }
//
//        setRecyclerView()
    }

//    private fun setRecyclerView() {
//        val mainActivity = this
//        taskViewModel.uncompletedTasks.observe(this) {
//            binding.taskListRecylerView.apply {
//                layoutManager = LinearLayoutManager(applicationContext)
//                adapter = TaskAdapter(it, mainActivity)
//            }
//        }
//    }

    override fun editTask(task: Task) {
        NewTaskSheet(task).show(supportFragmentManager, "newTaskTag")
    }

    override fun completeTask(task: Task) {
        taskViewModel.setCompleted(task)
    }
}