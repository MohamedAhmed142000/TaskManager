package com.example.taskmanager.domain.usecase

import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.domain.repo.TaskRepository
import kotlinx.coroutines.flow.Flow

//تحديث مهمة
class UpdateTaskUseCase(private val taskRepository: TaskRepository) {
 suspend operator fun invoke(task: Task) =taskRepository.updateTask(task)
}