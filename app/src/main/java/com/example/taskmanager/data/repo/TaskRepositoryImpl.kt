package com.example.taskmanager.data.repo

import com.example.taskmanager.data.local.TaskDao
import com.example.taskmanager.data.local.TaskEntity
import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.domain.repo.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {

    override fun getTasks(): Flow<List<Task>> {
        return taskDao.getTasks().map { entities ->
            entities.map { it.toTask() }
        }
    }

    override suspend fun addTask(task: Task) {
        taskDao.addTask(task.toEntity())
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task.toEntity())
    }

    override suspend fun deleteTask(id: Int) {
        taskDao.deleteTask(id)
    }
}