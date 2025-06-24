package com.example.taskmanager.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

//إنشاء قاعدة البيانات
    //تعريف قاعدة البيانات
@Database(
    //الجداول (في حالتنا جدول TaskEntity)
    entities = [TaskEntity::class],
    //رقم إصدار قاعدة البيانات
    version = 1,
    exportSchema = false
)
abstract class TaskDatabase : RoomDatabase() {
    //الطريقة اللي بتجيب DAO الخاص بالمهام
    abstract fun taskDao(): TaskDao

}