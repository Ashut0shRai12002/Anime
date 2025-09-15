package com.ashutosh.animeproject.repository

import android.util.Log
import com.ashutosh.animeproject.ApiService.ApiService
import com.ashutosh.animeproject.ApiService.RetrofitClient
import com.ashutosh.animeproject.Dao.AnimeDao
import com.ashutosh.animeproject.data.AnimeResponse
import com.ashutosh.animeproject.data.Entity.AnimeEntity
import kotlinx.coroutines.flow.Flow

class AnimeRepository(
    private val api: ApiService,
    private val dao: AnimeDao

) {
    fun getCachedAnime(): Flow<List<AnimeEntity>> = dao.getAllAnime()

    suspend fun refreshAnimeList() {
        try {
            try {

                // fetch from API

            val response = api.getTopAnime()
            val animeList = response.data
            val entities = animeList.map {
                AnimeEntity(
                    malId = it.mal_id,
                    title = it.title,
                    imageUrl = it.images.jpg?.image_url, // .jpg is non-null in API
                    score = it.score
                )
            }


                // save to DB
                dao.clearAll()
                dao.insertAll(entities)
                Log.d("RoomDB", "Data inserted successfully")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("RoomDB", "Failed to insert data: ${e.localizedMessage}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}
