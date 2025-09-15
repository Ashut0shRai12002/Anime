package com.ashutosh.animeproject.data

import androidx.room.Entity

@Entity(tableName = "studio")
data class Studio(
    val mal_id: Int,
    val name: String,
    val type: String,
    val url: String
)