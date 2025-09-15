package com.ashutosh.animeproject.data

import androidx.room.Embedded
import androidx.room.Entity

data class Prop(
    @Embedded(prefix = "from_") val from: From?,
    @Embedded(prefix = "to_") val to: To?
)