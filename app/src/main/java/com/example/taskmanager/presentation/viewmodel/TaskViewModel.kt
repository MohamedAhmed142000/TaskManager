package com.example.taskmanager.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.domain.usecase.AddTaskUseCase
import com.example.taskmanager.domain.usecase.DeleteTaskUseCase
import com.example.taskmanager.domain.usecase.GetTasksUseCase
import com.example.taskmanager.domain.usecase.UpdateTaskUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers

class TaskViewModel(
    //  (constructor) 4 UseCases
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()


    init {
        loadTasks()
    }
    private fun loadTasks() {
        viewModelScope.launch {
            getTasksUseCase().collect { taskList ->
                _tasks.value = taskList
            }
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            addTaskUseCase(task)
        }
    }


    fun deleteTask(taskId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteTaskUseCase(taskId)
        }
    }
//update task
    fun updateTask(task: Task) {
        viewModelScope.launch (Dispatchers.IO){
            updateTaskUseCase(task)
        }
    }
    private val _showOnlyIncomplete = MutableStateFlow(false)
    val showOnlyIncomplete = _showOnlyIncomplete.asStateFlow()

    fun toggleShowOnlyIncomplete() {
        _showOnlyIncomplete.value = !_showOnlyIncomplete.value
    }
}
