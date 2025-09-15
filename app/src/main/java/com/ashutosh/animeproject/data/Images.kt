package com.ashutosh.animeproject.data

import androidx.room.Embedded
import androidx.room.Entity


data class Images(
    @Embedded(prefix = "jpg_") val jpg: Jpg?,
    @Embedded(prefix = "webp_") val webp: Webp?
)
