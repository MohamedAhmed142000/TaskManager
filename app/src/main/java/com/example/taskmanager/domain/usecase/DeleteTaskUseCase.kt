package com.example.taskmanager.domain.usecase

import com.example.taskmanager.domain.repo.TaskRepository

//DeleteTaskUseCase
class DeleteTaskUseCase(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(id: Int) {
        taskRepository.deleteTask(id)
    }

}