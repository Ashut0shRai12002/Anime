package com.ashutosh.animeproject.data

data class TopAnimeResponse(
    val pagination: Pagination,
    val data: List<AnimeResponse>
)
