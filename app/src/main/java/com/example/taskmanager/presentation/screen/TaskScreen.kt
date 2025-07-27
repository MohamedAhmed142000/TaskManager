package com.example.taskmanager.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.presentation.viewmodel.TaskViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(viewModel: TaskViewModel, navController: NavController) {
    val tasks by remember { viewModel.tasks }.collectAsState()
    val showOnlyIncomplete by remember { viewModel.showOnlyIncomplete }.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var showAddDialog by remember { mutableStateOf(false) }
    var taskToEdit by remember { mutableStateOf<Task?>(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("مهامي", style = MaterialTheme.typography.titleLarge) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("المهام غير المنجزة", modifier = Modifier.weight(1f))
                    Switch(
                        checked = showOnlyIncomplete,
                        onCheckedChange = { viewModel.toggleShowOnlyIncomplete() }
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.onSurfaceVariant,
                contentColor = Color.White
            ) {
                Text("+")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {

                if (showAddDialog) {
                    TaskInputDialog(
                        onDismiss = { showAddDialog = false },
                        onConfirm = { title, desc ->
                            viewModel.addTask(Task(title = title, description = desc))
                            showAddDialog = false
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("تمت إضافة المهمة")
                            }
                        }
                    )
                }

                taskToEdit?.let { task ->
                    TaskInputDialog(
                        initialTitle = task.title,
                        initialDescription = task.description,
                        onDismiss = { taskToEdit = null },
                        onConfirm = { newTitle, newDesc ->
                            viewModel.updateTask(task.copy(title = newTitle, description = newDesc))
                            taskToEdit = null
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("تم تعديل المهمة")
                            }
                        }
                    )
                }

                val filteredTasks = if (showOnlyIncomplete) {
                    tasks.filter { !it.isCompleted }
                } else {
                    tasks
                }

                Spacer(modifier = Modifier.height(24.dp))

                if (filteredTasks.isEmpty()) {
                    Text("لا توجد مهام لعرضها", modifier = Modifier.padding(16.dp))
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 60.dp)
                    ) {
                        items(filteredTasks, key = { it.id }) { task ->
                            TaskItem(
                                task = task,
                                onDelete = { viewModel.deleteTask(task.id) },
                                onEdit = { taskToEdit = task },
                                onUpdate = { updatedTask -> viewModel.updateTask(updatedTask) }
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun TaskItem(task: Task, onDelete: () -> Unit, onEdit: () -> Unit, onUpdate: (Task) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF7A7A7A)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = task.isCompleted,
                    onCheckedChange = { onUpdate(task.copy(isCompleted = it)) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (task.description.isNotBlank()) {
                        Text(
                            text = task.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }
                Row {
                    IconButton(onClick = onEdit, colors = IconButtonDefaults.iconButtonColors( containerColor = Color(
                        0xFFB9B0B0
                    )
                    )) {
                        Icon(Icons.Default.Edit, contentDescription = "تعديل")
                    }
                    IconButton(onClick = onDelete, colors = IconButtonDefaults.iconButtonColors( containerColor = Color(
                        0xFFB9B0B0
                    ))) {
                        Icon(Icons.Default.Delete, contentDescription = "حذف")
                    }
                }
            }
        }
    }
}
