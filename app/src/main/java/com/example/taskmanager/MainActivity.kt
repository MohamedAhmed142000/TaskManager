package com.example.taskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.taskmanager.data.local.TaskDatabase
import com.example.taskmanager.data.repo.TaskRepositoryImpl
import com.example.taskmanager.domain.usecase.*
import com.example.taskmanager.presentation.screen.TaskScreen
import com.example.taskmanager.presentation.viewmodel.TaskViewModel
import com.example.taskmanager.presentation.viewmodel.TaskViewModelFactory
import com.example.taskmanager.ui.theme.TaskManagerTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        lifecycleScope.launch(Dispatchers.IO) {
            // Create database
            val database = Room.databaseBuilder(
                applicationContext,
                TaskDatabase::class.java,
                "task_db"
            ).build()

            // Setting repository and usecase
            val repository = TaskRepositoryImpl(database.taskDao())

            val getTasks = GetTasksUseCase(repository)
            val addTask = AddTaskUseCase(repository)
            val deleteTask = DeleteTaskUseCase(repository)
            val updateTask = UpdateTaskUseCase(repository)

            // ✅ Setting  ViewModelFactory
            val factory = TaskViewModelFactory(getTasks, addTask, deleteTask, updateTask)

            // ✅ Setting ViewModel
            val viewModel = ViewModelProvider(this@MainActivity, factory)[TaskViewModel::class.java]

            // ✅ veiw main screen in main threads
            withContext(Dispatchers.Main) {
                setContent {
                    TaskManagerTheme {
                        val navController = rememberNavController()
                        NavHost(navController = navController, startDestination = "task_list") {
                            composable("task_list") {
                                TaskScreen(viewModel = viewModel, navController = navController)
                            }

                        }
                    }
                }
            }
        }
    }
}

