package com.example.taskmanager.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.domain.usecase.AddTaskUseCase
import com.example.taskmanager.domain.usecase.DeleteTaskUseCase
import com.example.taskmanager.domain.usecase.GetTasksUseCase
import com.example.taskmanager.domain.usecase.UpdateTaskUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers

class TaskViewModel(
    //بياخد في البنية الأساسية (constructor) 4 UseCases
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase
) : ViewModel() {
    //بنعلن عن متغير داخلي اسمه _tasks من نوع MutableStateFlow وبيحفظ قائمة المهام.
    //
    //بعدين بنعمل نسخة مقروءة فقط اسمها tasks علشان نعرضها في الواجهة (UI) بدون ما نقدر نغيرها من برّا الـ ViewModel.
    //
    //✅ فايدة StateFlow: بتخلي Compose تتفاعل مع التغيرات في البيانات تلقائيًا.
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    //init بيتنفذ تلقائيًا أول ما الـ ViewModel يشتغل.
    //
    //بينادي على loadTasks() علشان نبدأ نعرض المهام من قاعدة البيانات على طول.
    init {
        loadTasks()
    }
//loadTasks() بتفتح كوروتين داخل viewModelScope (يعني مرتبط بعمر الشاشة).
//
//getTasksUseCase() بيرجع Flow من المهام.
//
//بنستخدم collect علشان نسمع للتغيرات اللي بتحصل على المهام.
//
//كل مرة البيانات تتغير → بنحدث _tasks ← وده بيخلّي الـ UI يتحدث تلقائيًا.
    private fun loadTasks() {
        viewModelScope.launch {
            getTasksUseCase().collect { taskList ->
                _tasks.value = taskList
            }
        }
    }

    //دالة عامة تقدر تناديها من واجهة المستخدم (مثل زر "إضافة").
    //
    //بتستخدم الكوروتين علشان تنفذ addTaskUseCase بدون توقف للتطبيق.
    fun addTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            addTaskUseCase(task)
        }
    }

    //تحذف مهمة حسب رقمها id.
    fun deleteTask(taskId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteTaskUseCase(taskId)
        }
    }
//تعدّل بيانات المهمة الموجودة (مثلاً: تغيّر عنوانها أو حالة التنفيذ).
    fun updateTask(task: Task) {
        viewModelScope.launch (Dispatchers.IO){
            updateTaskUseCase(task)
        }
    }
}
