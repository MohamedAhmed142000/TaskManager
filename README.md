# 📝 TaskManager App

A simple and scalable **Task Management Android App** built with **Kotlin**, **Jetpack Compose**, and **Clean Architecture**.  
The app allows users to manage daily tasks with support for **categories** and **reminders**.

---

## 🚀 Features

- ✅ Add, edit, and delete tasks
- ✅ Mark tasks as **completed / not completed**
- ✅ Filter tasks by **status** (All, Completed, Pending)
- ✅ Organize tasks into **categories** (e.g., Work, Personal, Study)
- ✅ Set **reminders** for tasks using system notifications
- ✅ Smooth UI built with **Material 3** + animations
- ✅ Offline-first with **Room Database**
- ✅ Fully tested with **JUnit + MockK + Compose UI Tests**

---

## 🏛 Architecture


This project follows **Clean Architecture + MVVM** with clear separation of concerns:

- **Domain Layer** → Business logic (UseCases, Models)
- **Data Layer** → Local database (Room), Repository implementations
- **Presentation Layer** → Jetpack Compose UI + ViewModels (StateFlow)

---

## 🛠 Tech Stack

- **Kotlin** – Modern Android language
- **Jetpack Compose** – Declarative UI toolkit
- **Room Database** – Local persistence
- **Coroutines + Flow** – Asynchronous programming
- **Navigation Compose** – In-app navigation
- **ViewModel + StateFlow** – State management
- **WorkManager / AlarmManager** – Task reminders & notifications
- **JUnit + MockK** – Unit testing
- **Espresso / Compose Test** – UI testing

---

## 🧪 Testing

### Unit Tests
- ✅ AddTaskUseCaseTest
- ✅ GetTasksUseCaseTest
- ✅ DeleteTaskUseCaseTest
- ✅ CategoryUseCasesTest
- ✅ ReminderSchedulerTest

### UI Tests
- ✅ Add and edit task flow
- ✅ Filtering tasks by category
- ✅ Checking reminder notification trigger

---

