package com.example.zametki.db

data class DbItem(
    val id: Int,
    val title: String,
    val content: String,
    val star: Int,
    val pass: String,
    val date: String
)
