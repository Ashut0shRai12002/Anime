package com.ashutosh.animeproject.data

import androidx.room.Embedded

data class Aired(
    val from: String,
    @Embedded(prefix = "prop_") val prop: Prop,
    val string: String,
    val to: String
)