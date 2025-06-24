package com.example.taskmanager.presentation.viewmodel

import androidx.lifecycle.*
import com.example.taskmanager.domain.usecase.*

class TaskViewModelFactory(
    private val getTasks: GetTasksUseCase,
    private val addTask: AddTaskUseCase,
    private val deleteTask: DeleteTaskUseCase,
    private val updateTask: UpdateTaskUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(getTasks, addTask, deleteTask, updateTask) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
