package com.example.taskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.taskmanager.data.local.TaskDatabase
import com.example.taskmanager.data.repo.TaskRepositoryImpl
import com.example.taskmanager.domain.usecase.*
import com.example.taskmanager.presentation.screen.EditTaskScreen
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

        // 🛠 تأجيل كل شيء لحين تحميل قاعدة البيانات
        lifecycleScope.launch(Dispatchers.IO) {
            // ✅ إنشاء قاعدة البيانات في IO
            val database = Room.databaseBuilder(
                applicationContext,
                TaskDatabase::class.java,
                "task_db"
            ).build()

            // ✅ إنشاء Repository و UseCases
            val repository = TaskRepositoryImpl(database.taskDao())

            val getTasks = GetTasksUseCase(repository)
            val addTask = AddTaskUseCase(repository)
            val deleteTask = DeleteTaskUseCase(repository)
            val updateTask = UpdateTaskUseCase(repository)

            // ✅ إنشاء ViewModelFactory
            val factory = TaskViewModelFactory(getTasks, addTask, deleteTask, updateTask)

            // ✅ إنشاء ViewModel بشكل آمن
            val viewModel = ViewModelProvider(this@MainActivity, factory)[TaskViewModel::class.java]

            // ✅ عرض الواجهة على Main Thread بعد ما كل شيء جهز
            withContext(Dispatchers.Main) {
                setContent {
                    TaskManagerTheme {
                        val navController = rememberNavController()

                        NavHost(navController = navController, startDestination = "task_list") {
                            composable("task_list") {
                                TaskScreen(viewModel = viewModel, navController = navController)
                            }

                            composable("edit_task/{taskId}") { backStackEntry ->
                                val taskId = backStackEntry.arguments?.getString("taskId")?.toIntOrNull()
                                taskId?.let {
                                    EditTaskScreen(taskId = it, viewModel = viewModel, navController = navController)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

