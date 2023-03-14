package com.example.hack_todolist.Models

import kotlinx.serialization.Serializable


data class User(val id: Int,
                val email: String,
                val username: String,
                val firstName: String,
                val lastName: String,
                )
