package com.ashutosh.animeproject.data

import androidx.room.Embedded
import androidx.room.Entity

data class Trailer(
    val youtube_id: String?, val url: String?, val embed_url: String?,
    @Embedded(prefix = "images_") val images: ImagesX?
)