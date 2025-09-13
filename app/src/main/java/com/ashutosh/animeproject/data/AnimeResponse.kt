package com.ashutosh.animeproject.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime_table")
data class AnimeResponse(
    @Embedded(prefix = "aired_") val aired: Aired,
    val airing: Boolean,
    val approved: Boolean,
    val background: String,
    @Embedded(prefix = "broadcast_") val broadcast: Broadcast,
    val demographics: List<Demographic>,
    val duration: String,
    val episodes: Int,
    val explicit_genres: List<Genre>,
    val favorites: Int,
    val genres: List<Genre>,
    @Embedded(prefix = "images_") val images: Images,
    val licensors: List<Licensor>,
    @PrimaryKey val mal_id: Int,
    val members: Int,
    val popularity: Int,
    val producers: List<Producer>,
    val rank: Int,
    val rating: String,
    val score: Double,
    val scored_by: Int,
    val season: String,
    val source: String,
    val status: String,
    val studios: List<Studio>,
    val synopsis: String,
    val themes: List<Theme>,
    val title: String,
    val title_english: String,
    val title_japanese: String,
    val title_synonyms: List<String>,
    val titles: List<Title>,
    @Embedded(prefix = "trailer_") val trailer: Trailer,
    val type: String,
    val url: String,
    val year: Int
)