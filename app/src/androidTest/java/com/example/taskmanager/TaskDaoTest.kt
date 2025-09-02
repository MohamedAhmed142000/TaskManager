package com.example.taskmanager

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.taskmanager.data.local.TaskDao
import com.example.taskmanager.data.local.TaskDatabase
import com.example.taskmanager.data.repo.toEntity
import com.example.taskmanager.domain.model.Task
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TaskDaoTest {

    private lateinit var db: TaskDatabase
    private lateinit var dao: TaskDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, TaskDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.taskDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun addTask_andGetTasks() = runBlocking {
        val task = Task(id = 1, title = "Test Task", description = "Testing add", isCompleted = false).toEntity()
        dao.addTask(task)

        val tasks = dao.getTasks().first()
        assertTrue(tasks.contains(task))
    }

    @Test
    fun deleteTask_andCheckList() = runBlocking {
        val task = Task(id = 1, title = "Delete Me", description = "Testing delete", isCompleted = false).toEntity()
        dao.addTask(task)
        dao.deleteTask(1)

        val tasks = dao.getTasks().first()
        assertFalse(tasks.contains(task))
    }

}