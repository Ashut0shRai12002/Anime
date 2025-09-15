package com.ashutosh.animeproject.data

import androidx.room.Entity

@Entity(tableName = "title")
data class Title(
    val title: String,
    val type: String
)