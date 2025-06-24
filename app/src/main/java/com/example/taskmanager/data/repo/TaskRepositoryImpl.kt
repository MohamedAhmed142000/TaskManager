package com.example.taskmanager.data.repo

import com.example.taskmanager.data.local.TaskDao
import com.example.taskmanager.data.local.TaskEntity
import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.domain.repo.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//اللي هينفّذ الواجهة اللي كتبناها في domain/repo/TaskRepository.kt
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

    //في deleteTask إحنا بنمرر كائن TaskEntity فقط برقم الـ ID، لكن في الواقع الأفضل يكون:
    //
    //نجيب المهمة أولاً من الـ DB
    //
    //أو نعدل DAO ليقبل @Query("DELETE FROM tasks WHERE id = :id")
    override suspend fun deleteTask(id: Int) {
        val taskEntity = TaskEntity(id = id, title = "", description = "")
        taskDao.deleteTask(taskEntity)
    }
}