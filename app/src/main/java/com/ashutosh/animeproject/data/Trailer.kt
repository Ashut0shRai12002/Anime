package com.ashutosh.animeproject.data

import androidx.room.Embedded

data class Trailer(
    val embed_url: String,
    @Embedded(prefix = "images_") val images: ImagesX,
    val url: String,
    val youtube_id: String
)