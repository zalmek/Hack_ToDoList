package com.example.hack_todolist

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hack_todolist.Dao.EventDao
import com.example.hack_todolist.Entities.Event

@Database(entities = [Event::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): EventDao
}