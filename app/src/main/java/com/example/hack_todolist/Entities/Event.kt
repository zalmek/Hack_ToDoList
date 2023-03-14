package com.example.hack_todolist.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hack_todolist.Tag
import java.time.Instant

@Entity
data class Event(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "createdAt") val createdAt: Long = Instant.now().epochSecond,
    @ColumnInfo(name = "color") val color: String,
    @ColumnInfo(name = "startTime") val startTime: Long,
    @ColumnInfo(name = "endTime") val endTime: Long,
    @ColumnInfo(name = "tags") val tags: String,
    @PrimaryKey(autoGenerate = true) val uid: Int = 0
)
