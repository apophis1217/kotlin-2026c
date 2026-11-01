
package com.appweek07

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var radioGroup: RadioGroup
    private lateinit var listView: ListView
    private lateinit var editTextInput: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var spinnerPriority: Spinner
    private lateinit var layoutTaskControls: LinearLayout
    private lateinit var buttonAdd: Button
    private lateinit var buttonClear: Button
    private lateinit var textViewInfo: TextView

    private lateinit var studentList: ArrayList<String>
    private lateinit var taskList: ArrayList<Task>

    private lateinit var studentAdapter: ArrayAdapter<String>
    private lateinit var taskAdapter: ArrayAdapter<Task>

    private var currentMode = AppMode.STUDENT_LIST

    companion object {
        private const val TAG = "KotlinWeek07App"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentList = ArrayList()
        taskList = ArrayList()

        setupViews()
        setupAdapters()
        setupListeners()
        addInitialData()
        setMode(AppMode.STUDENT_LIST)
    }

    private fun setupViews() {
        radioGroup = findViewById(R.id.radioGroup)
        listView = findViewById(R.id.listView)
        editTextInput = findViewById(R.id.editTextInput)
        editTextDescription = findViewById(R.id.editTextDescription)
        spinnerPriority = findViewById(R.id.spinnerPriority)
        layoutTaskControls = findViewById(R.id.layoutTaskControls)
        buttonAdd = findViewById(R.id.buttonAdd)
        buttonClear = findViewById(R.id.buttonClear)
        textViewInfo = findViewById(R.id.textViewInfo)
    }

    private fun setupAdapters() {
        studentAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            studentList
        )

        taskAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            taskList
        )

        val priorities = TaskPriority.values().map {
            it.displayName
        }

        val priorityAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            priorities
        )

        priorityAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        spinnerPriority.adapter = priorityAdapter
    }

    private fun setupListeners() {
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioStudentList ->
                    setMode(AppMode.STUDENT_LIST)

                R.id.radioTaskManager ->
                    setMode(AppMode.TASK_MANAGER)
            }
        }

        buttonAdd.setOnClickListener {
            when (currentMode) {
                AppMode.STUDENT_LIST -> addStudent()
                AppMode.TASK_MANAGER -> addTask()
            }
        }

        buttonClear.setOnClickListener {
            clearAll()
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            when (currentMode) {
                AppMode.STUDENT_LIST -> {
                    val name = studentList[position]
                    showToast("Selected: $name")
                }

                AppMode.TASK_MANAGER -> {
                    toggleTask(position)
                }
            }
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->
            showDeleteDialog(position)
            true
        }
    }

    private fun setMode(mode: AppMode) {
        currentMode = mode

        editTextInput.text.clear()

        when (mode) {
            AppMode.STUDENT_LIST -> {
                editTextInput.hint = "Enter student name"
                buttonAdd.text = "Add Student"

                layoutTaskControls.visibility = View.GONE
                listView.adapter = studentAdapter
                radioGroup.check(R.id.radioStudentList)
            }

            AppMode.TASK_MANAGER -> {
                editTextInput.hint = "Enter task title"
                buttonAdd.text = "Add Task"

                layoutTaskControls.visibility = View.VISIBLE
                listView.adapter = taskAdapter
                radioGroup.check(R.id.radioTaskManager)
            }
        }

        updateInfoDisplay()
        Log.d(TAG, "Mode: ${mode.displayName}")
    }

    private fun addStudent() {
        val name = editTextInput.text.toString().trim()

        if (name.isEmpty()) {
            showToast("Please enter a student name")
            return
        }

        if (studentList.contains(name)) {
            showToast("Student already exists")
            return
        }

        studentList.add(name)
        studentAdapter.notifyDataSetChanged()

        editTextInput.text.clear()
        updateInfoDisplay()

        showToast("Added student: $name")
    }

    private fun addTask() {
        val title = editTextInput.text.toString().trim()
        val description =
            editTextDescription.text.toString().trim()

        if (title.isEmpty()) {
            showToast("Please enter a task title")
            return
        }

        val selectedPriority =
            TaskPriority.values()[spinnerPriority.selectedItemPosition]

        val task = Task(
            title = title,
            description = description,
            priority = selectedPriority
        )

        taskList.add(task)
        taskAdapter.notifyDataSetChanged()

        editTextInput.text.clear()
        editTextDescription.text.clear()

        updateInfoDisplay()
        showToast("Added task: $title")

        Log.d(TAG, "Task added: $title")
    }

    private fun toggleTask(position: Int) {
        val task = taskList[position]

        task.isCompleted = !task.isCompleted
        taskAdapter.notifyDataSetChanged()

        updateInfoDisplay()

        val status =
            if (task.isCompleted) "Completed" else "Pending"

        showToast("$status: ${task.title}")

        Log.d(TAG, "Task status changed: $status")
    }

    private fun showDeleteDialog(position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Delete")
            .setMessage("Delete selected item?")
            .setPositiveButton("Delete") { _, _ ->
                removeItem(position)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun removeItem(position: Int) {
        when (currentMode) {
            AppMode.STUDENT_LIST -> {
                if (position in studentList.indices) {
                    studentList.removeAt(position)
                    studentAdapter.notifyDataSetChanged()
                }
            }

            AppMode.TASK_MANAGER -> {
                if (position in taskList.indices) {
                    taskList.removeAt(position)
                    taskAdapter.notifyDataSetChanged()
                }
            }
        }

        updateInfoDisplay()
    }

    private fun clearAll() {
        when (currentMode) {
            AppMode.STUDENT_LIST -> {
                studentList.clear()
                studentAdapter.notifyDataSetChanged()
            }

            AppMode.TASK_MANAGER -> {
                taskList.clear()
                taskAdapter.notifyDataSetChanged()
            }
        }

        updateInfoDisplay()
        showToast("All items cleared")
    }

    private fun updateInfoDisplay() {
        when (currentMode) {
            AppMode.STUDENT_LIST -> {
                textViewInfo.text =
                    "Total Students: ${studentList.size}"
            }

            AppMode.TASK_MANAGER -> {
                val completed = taskList.count {
                    it.isCompleted
                }

                textViewInfo.text =
                    "Tasks: ${taskList.size} | Done: $completed"
            }
        }
    }

    private fun addInitialData() {
        studentList.addAll(
            listOf("Kim", "Lee", "Park")
        )

        studentAdapter.notifyDataSetChanged()
    }

    private fun showToast(message: String) {
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}
