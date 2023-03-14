package com.example.hack_todolist.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable

@Entity
data class User(@PrimaryKey val uid: Int,
                @ColumnInfo(name = "username") val username: String,
                @ColumnInfo(name = "password_hash")val password_hash: Long)
