package com.example.taskmanager.domain.usecase

import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.domain.repo.TaskRepository
import kotlinx.coroutines.flow.Flow

//عرض كل المهام
class GetTasksUseCase(private val taskRepository: TaskRepository) {
    operator fun invoke(): Flow<List<Task>> = taskRepository.getTasks()

}