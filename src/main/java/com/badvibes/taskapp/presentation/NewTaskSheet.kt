package com.badvibes.taskapp.presentation

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.badvibes.taskapp.R
import com.badvibes.taskapp.TaskApp
import com.badvibes.taskapp.databinding.FragmentNewTaskSheetBinding
import com.badvibes.taskapp.data.model.Task
import com.badvibes.taskapp.presentation.components.TaskModelFactory
import com.badvibes.taskapp.presentation.components.TaskViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalTime

class NewTaskSheet(var task: Task?) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentNewTaskSheetBinding
    private val taskViewModel: TaskViewModel by viewModels {
        TaskModelFactory((requireActivity().application as TaskApp).repo)
    }
    private var dueTime: LocalTime? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewTaskSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        if (task != null) {
            binding.taskTitle.text = getString(R.string.editTask)

            val editable = Editable.Factory.getInstance()

            binding.name.text = editable.newEditable(task!!.name)
            binding.desc.text = editable.newEditable(task!!.desc)

            if (task!!.dueTime() != null) {
                dueTime = task!!.dueTime()!!
                updateTimeButtonText()
            }
        } else {
            binding.deleteButton.isVisible = false
            binding.taskTitle.text = getString(R.string.newTask)
        }

//        taskViewModel = ViewModelProvider(activity)[TaskViewModel::class.java]
        binding.deleteButton.setOnClickListener{
            taskViewModel.deleteTask(task!!)
            dismiss()
        }
        binding.saveButton.setOnClickListener {
            if (task != null) {
                taskViewModel.updateTask(task!!, binding, dueTime)
            } else {
                taskViewModel.addTask(binding, dueTime)
            }
            dismiss()
        }
        binding.timePickerButton.setOnClickListener{

            if (dueTime == null)
                dueTime = LocalTime.now()
            openTimePicker()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        return dialog
    }

    private fun openTimePicker() {
        val listener = TimePickerDialog.OnTimeSetListener{ _, selectedHour, selectedMinute ->
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
}