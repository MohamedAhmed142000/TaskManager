package com.example.taskmanager

import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.domain.repo.TaskRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class TaskRepositoryTest {

    private val mockRepo = mockk<TaskRepository>()

    // 1️⃣ Get all tasks
    @Test
    fun `getTasks should return list of tasks`() = runTest {
        // Arrange
        val fakeTasks = listOf(
            Task(id = 1, title = "Task 1", description = "Desc 1", isCompleted = false),
            Task(id = 2, title = "Task 2", description = "Desc 2", isCompleted = true)
        )
        coEvery { mockRepo.getTasks() } returns flowOf(fakeTasks)

        // Act
        val result = mockRepo.getTasks()

        // Assert
        result.collect { tasks ->
            assertEquals(2, tasks.size)
            assertEquals("Task 1", tasks[0].title)
        }
    }

    // 2️⃣ Add new task
    @Test
    fun `addTask should call repository method`() = runTest {
        // Arrange
        val newTask = Task(id = 3, title = "New Task", description = "New Desc", isCompleted = false)
        coEvery { mockRepo.addTask(newTask) } returns Unit

        // Act
        mockRepo.addTask(newTask)

        // Assert
        coVerify { mockRepo.addTask(newTask) }
    }

    // 3️⃣ Update task
    @Test
    fun `updateTask should call repository method`() = runTest {
        // Arrange
        val updatedTask = Task(id = 1, title = "Updated Task", description = "Updated Desc", isCompleted = true)
        coEvery { mockRepo.updateTask(updatedTask) } returns Unit

        // Act
        mockRepo.updateTask(updatedTask)

        // Assert
        coVerify { mockRepo.updateTask(updatedTask) }
    }

    // 4️⃣ Delete task
    @Test
    fun `deleteTask should call repository method`() = runTest {
        // Arrange
        val taskId = 1
        coEvery { mockRepo.deleteTask(taskId) } returns Unit

        // Act
        mockRepo.deleteTask(taskId)

        // Assert
        coVerify { mockRepo.deleteTask(taskId) }
    }
}
