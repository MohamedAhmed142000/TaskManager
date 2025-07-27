package com.example.taskmanager.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

//create Databse

@Database(
    //tables use in database
    entities = [TaskEntity::class],
    //version in database
    version = 1,
    exportSchema = false
)
abstract class TaskDatabase : RoomDatabase() {
    //DAO function
    abstract fun taskDao(): TaskDao

}