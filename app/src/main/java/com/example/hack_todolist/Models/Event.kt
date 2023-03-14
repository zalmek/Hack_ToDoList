package com.example.hack_todolist.Models

import com.example.hack_todolist.Tag
import java.time.Instant

data class Event(
    val title: String,
    val description: String,
    val createdAt: Long = Instant.now().epochSecond,
    val color: String,
    val startTime: Long,
    val endTime: Long,
    val tags: List<Tag>) {
}