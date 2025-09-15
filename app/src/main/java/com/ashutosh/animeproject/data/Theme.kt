package com.ashutosh.animeproject.data

import androidx.room.Entity


@Entity(tableName = "theme")
data class Theme(
    val mal_id: Int,
    val name: String,
    val type: String,
    val url: String
)