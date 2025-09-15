package com.ashutosh.animeproject.data

import androidx.room.Entity
import androidx.room.ForeignKey

data class Demographic(
    val mal_id: Int,
    val name: String,
    val type: String,
    val url: String
)