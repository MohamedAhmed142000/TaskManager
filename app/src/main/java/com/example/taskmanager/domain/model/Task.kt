package com.example.taskmanager.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Task(
    // number of tasks
    val id: Int = 0,
    // title of task
    val title: String,
    // description of task
    val description: String="",
    // date of task
    val isCompleted: Boolean = false
)