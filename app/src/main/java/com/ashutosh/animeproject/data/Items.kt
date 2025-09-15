package com.ashutosh.animeproject.data

import androidx.room.Entity

data class Items(
    val count: Int,
    val per_page: Int,
    val total: Int
)