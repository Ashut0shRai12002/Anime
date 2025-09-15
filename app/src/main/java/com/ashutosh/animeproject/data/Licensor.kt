package com.ashutosh.animeproject.data

import androidx.room.Entity

data class Licensor(
    val mal_id: Int,
    val name: String,
    val type: String,
    val url: String
)