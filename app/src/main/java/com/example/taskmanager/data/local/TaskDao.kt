package com.example.taskmanager.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

//Room
@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getTasks(): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTask(task: TaskEntity)

    @Query("DELETE FROM tasks WHERE id = :id")
    fun deleteTask(id: Int)

    @Update
    fun updateTask(task: TaskEntity)

}