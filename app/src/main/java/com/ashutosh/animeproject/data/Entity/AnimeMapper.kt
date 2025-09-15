package com.ashutosh.animeproject.data.Entity

import com.ashutosh.animeproject.data.AnimeResponse

fun AnimeResponse.toUiModel() = AnimeUiModel(
    id = mal_id,
    title = title,
    imageUrl = images?.jpg?.image_url,
    score = score,
    mal_id = mal_id.toString() // or just mal_id if it's already String

)

fun AnimeEntity.toUiModel() = AnimeUiModel(
    id = malId,
    title = title,
    imageUrl = imageUrl,
    score = score,
    mal_id = malId.toString() // or just mal_id if it's already String

)
