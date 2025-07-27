package com.example.taskmanager.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

//shapes of tables in database
@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false
)