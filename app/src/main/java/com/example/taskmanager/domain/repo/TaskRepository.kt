package com.example.taskmanager.domain.repo

import com.example.taskmanager.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    //تجيب كل المهام (بتبعت Flow يعني بيانات حية تتحدث تلقائيًا)
    fun getTasks(): Flow<List<Task>>

    //تضيف مهمة جديدة
    suspend fun addTask(task: Task)

    //تعدّل بيانات مهمة موجودة
    suspend fun updateTask(task: Task)

    // تمسح مهمة حسب رقمها
    suspend fun deleteTask(id: Int)

}