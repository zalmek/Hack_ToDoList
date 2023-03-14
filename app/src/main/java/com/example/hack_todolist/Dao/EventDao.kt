package com.example.hack_todolist.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.hack_todolist.Entities.Event

@Dao
interface EventDao {
    @Query("SELECT * FROM event")
    fun getAll(): List<Event>

    @Insert
    fun insertAll(vararg users: Event)

    @Delete
    fun delete(user: Event)

    @Query("DELETE FROM event")
    fun deleteAll()
}