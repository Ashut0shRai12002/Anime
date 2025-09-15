package com.ashutosh.animeproject.data

import androidx.room.Entity

@Entity(tableName = "top_anime_response")
data class TopAnimeResponse(
    val pagination: Pagination,
    val data: List<AnimeResponse>
)
