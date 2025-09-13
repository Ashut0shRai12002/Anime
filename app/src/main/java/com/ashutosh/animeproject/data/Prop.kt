package com.ashutosh.animeproject.data

import androidx.room.Embedded

data class Prop(
    @Embedded(prefix = "from_") val from: From,
    @Embedded(prefix = "to_") val to: To
)
