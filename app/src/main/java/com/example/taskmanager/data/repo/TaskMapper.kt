package com.example.taskmanager.data.repo

import com.example.taskmanager.data.local.TaskEntity
import com.example.taskmanager.domain.model.Task

// mapper between  Task and TaskEntity
fun TaskEntity.toTask(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted)
}
fun Task.toEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted)
}