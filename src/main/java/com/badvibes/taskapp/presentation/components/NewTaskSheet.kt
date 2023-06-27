package com.badvibes.taskapp.presentation.components

import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.badvibes.taskapp.databinding.FragmentNewTaskSheetBinding
import com.badvibes.taskapp.domain.model.Task
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalTime

class NewTaskSheet(var task: Task?) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentNewTaskSheetBinding
    private lateinit var taskViewModel: TaskViewModel
    private var dueTime: LocalTime? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        if (task != null) {
            binding.taskTitle.text = "Edit Task"

            val editable = Editable.Factory.getInstance()

            binding.name.text = editable.newEditable(task!!.name)
            binding.desc.text = editable.newEditable(task!!.desc)

            if (task!!.dueTime() != null) {
                dueTime = task!!.dueTime()!!
                updateTimeButtonText()
            }
        } else {
            binding.deleteButton.isVisible = false
            binding.taskTitle.text = "New Task"
        }

        taskViewModel = ViewModelProvider(activity)[TaskViewModel::class.java]
        binding.deleteButton.setOnClickListener{
            deleteAction()
        }
        binding.saveButton.setOnClickListener {
            saveAction()
        }
        binding.timePickerButton.setOnClickListener{
            if (dueTime == null)
                dueTime = LocalTime.now()
            openTimePicker()
        }
    }

    private fun openTimePicker() {
//        if (dueTime == null)
//            dueTime = LocalTime.now()
        val listener = TimePickerDialog.OnTimeSetListener{_, selectedHour, selectedMinute ->
            dueTime = LocalTime.of(selectedHour, selectedMinute)
            updateTimeButtonText()
        }
        val dialog = TimePickerDialog(activity, listener, dueTime!!.hour, dueTime!!.minute, true)
        dialog.setTitle("Task Due")
        dialog.show()
    }

    private fun updateTimeButtonText() {
        binding.timePickerButton.text = String.format("%02d:%02d", dueTime!!.hour, dueTime!!.minute)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewTaskSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun saveAction() {
        val name = binding.name.text.toString()
        val desc = binding.desc.text.toString()
        val dueTimeString = if (dueTime == null) null else Task.timeFormatter.format(dueTime)

        if (task == null) {
            val newTask = Task(name, desc, dueTimeString, null)
            taskViewModel.addTask(newTask)
        } else {
            task!!.name = name
            task!!.desc = desc
            taskViewModel.updateTask(task!!)
        }

        binding.name.setText("")
        binding.desc.setText("")
        dismiss()
    }

    private fun deleteAction() {
        if (task == null) {
            dismiss()
            return
        }

        taskViewModel.deleteTask(task!!)
        dismiss()
    }
}