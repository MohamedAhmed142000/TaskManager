package com.example.taskmanager


import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.taskmanager.data.local.TaskDao
import com.example.taskmanager.data.local.TaskDatabase
import com.example.taskmanager.data.repo.TaskRepositoryImpl
import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.domain.usecase.AddTaskUseCase
import com.example.taskmanager.domain.usecase.DeleteTaskUseCase
import com.example.taskmanager.domain.usecase.GetTasksUseCase
import com.example.taskmanager.domain.usecase.UpdateTaskUseCase
import com.example.taskmanager.presentation.viewmodel.TaskViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TaskViewModelTest {

    private lateinit var viewModel: TaskViewModel
    private lateinit var repository: TaskRepositoryImpl
    private lateinit var taskDao: TaskDao
    private lateinit var db: TaskDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            TaskDatabase::class.java
        ).allowMainThreadQueries().build()

        taskDao = db.taskDao()
        repository = TaskRepositoryImpl(taskDao)

        val getTasksUseCase = GetTasksUseCase(repository)
        val addTaskUseCase = AddTaskUseCase(repository)
        val updateTaskUseCase = UpdateTaskUseCase(repository)
        val deleteTaskUseCase = DeleteTaskUseCase(repository)

        viewModel = TaskViewModel(
            getTasksUseCase,
            addTaskUseCase,
            deleteTaskUseCase,
            updateTaskUseCase
        )
    }

    @Test
    fun addTask_updatesTaskList() = runBlocking {
        val task = Task(1, "VM Task", "Task from ViewModel", false)
        viewModel.addTask(task)

        val tasks = repository.getTasks().first()
        assertEquals(1, tasks.size)
        assertEquals(task, tasks.first())
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteTask_updatesTaskList() = runBlocking {
        val task = Task(1, "VM Task", "Task from ViewModel", false)
        viewModel.addTask(task)
        viewModel.deleteTask(task.id)
        val tasks = repository.getTasks().first()
        assertEquals(0, tasks.size)
        assertFalse(tasks.contains(task))
    }
    @Test
    fun updateTask_updatesTaskList() = runBlocking {
        val task = Task(1, "VM Task", "Task from ViewModel", false)
        viewModel.addTask(task)
        val updatedTask = task.copy(title = "Updated VM Task")
        viewModel.updateTask(updatedTask)
        val tasks = repository.getTasks().first()
        assertEquals(1, tasks.size)
        assertEquals(updatedTask, tasks.first())
    }
    @Test
    fun toggleShowOnlyIncomplete_updatesShowOnlyIncomplete() = runBlocking {
        viewModel.toggleShowOnlyIncomplete()
        val showOnlyIncomplete = viewModel.showOnlyIncomplete.value
        assertTrue(showOnlyIncomplete)
    }

    @After
    fun tearDown() {
        db.close()
    }

}
