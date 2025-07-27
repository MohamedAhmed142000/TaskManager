package com.example.taskmanager.domain.repo

import com.example.taskmanager.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    //Get all tasks (Flow means live data that is automatically updated)
    fun getTasks(): Flow<List<Task>>

    //add new task
    suspend fun addTask(task: Task)

    //update task
    suspend fun updateTask(task: Task)

    // delete task with id
    suspend fun deleteTask(id: Int)

}