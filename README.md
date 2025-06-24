                                                        TaskManager – Simple Task Management App
TaskManager is a clean and modern Android application built with Jetpack Compose and Clean Architecture, designed to help users manage their daily tasks efficiently.
 -> Features
      Add new tasks with title and optional description
      Edit or delete existing tasks
      Mark tasks as completed using a checkbox
      Filter tasks by status: All, Completed, or Incomplete
      Smooth UI with Material 3 and subtle animations
      Local storage with Room database
->Tech Stack
      Kotlin – Programming language
      Jetpack Compose – Declarative UI framework
      Room – Local database (SQLite abstraction)
      Coroutines + Flow – Asynchronous data stream handling
      MVVM + Clean Architecture – Scalable, maintainable code structure
      Navigation Compose – Screen navigation
      StateFlow + ViewModel – Reactive state management
->Architecture
      data – Data layer (Room, repositories)
      domain – Business logic layer (UseCases, models)
      presentation – UI layer (screens, ViewModels)
