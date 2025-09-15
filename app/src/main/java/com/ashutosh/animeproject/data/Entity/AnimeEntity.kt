package com.ashutosh.animeproject.data.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime_table")
data class AnimeEntity(
    @PrimaryKey val malId: Int,
    val title: String,
    val imageUrl: String?,
    val score: Double?
)