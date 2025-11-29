package com.example.sis4final

import androidx.room.Entity //библиотека для Entity
import androidx.room.PrimaryKey //библиотека для PrimaryKey

@Entity(tableName = "posts") //
data class Post(
    val userId: Int,
    @PrimaryKey val id: Int, //задаём уникальный ключ
    val title: String,
    val body: String
)
