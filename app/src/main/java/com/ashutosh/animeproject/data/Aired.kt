package com.ashutosh.animeproject.data

import androidx.room.Embedded
import androidx.room.Entity

data class Aired(
    val from: String?,
    val to: String?,
    val string: String?,
    @Embedded(prefix = "prop_") val prop: Prop?
)
