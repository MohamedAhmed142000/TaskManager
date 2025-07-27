package com.example.taskmanager.domain.usecase

import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.domain.repo.TaskRepository

//AddTaskUseCase
class AddTaskUseCase(private val taskRepository: TaskRepository)  {
    suspend operator fun invoke(task: Task) {
        taskRepository.addTask(task)
    }
}