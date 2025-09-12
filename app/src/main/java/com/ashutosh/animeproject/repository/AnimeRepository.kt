package com.ashutosh.animeproject.repository

import com.ashutosh.animeproject.ApiService.RetrofitClient
import com.ashutosh.animeproject.Dao.AnimeDao
import com.ashutosh.animeproject.data.Entity.AnimeEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class AnimeRepository(private val dao: AnimeDao) {

    fun getTopAnime(): Flow<List<AnimeEntity>> = flow {
        try {
            val response = RetrofitClient.api.getTopAnime()
            val animeEntities = response.data.map {
                AnimeEntity(
                    malId = it.mal_id,
                    title = it.title,
                    imageUrl = it.images.jpg.image_url,
                    score = it.score,
                    synopsis = it.synopsis
                )
            }
            dao.clearAll()
            dao.insertAll(animeEntities)
            emit(animeEntities) // emit fresh data
        } catch (e: Exception) {
            // if network fails → fallback to DB
            val cached = dao.getAllAnime().first()
            emit(cached)
        }
    }
}
