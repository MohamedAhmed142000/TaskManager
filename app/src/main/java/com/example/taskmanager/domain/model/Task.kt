package com.example.taskmanager.domain.model

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