package com.ashutosh.animeproject.repository

import android.util.Log
import com.ashutosh.animeproject.ApiService.ApiService
import com.ashutosh.animeproject.ApiService.RetrofitClient
import com.ashutosh.animeproject.Dao.AnimeDao
import com.ashutosh.animeproject.data.AnimeResponse

class AnimeRepository(
    private val api: ApiService,
    private val dao: AnimeDao
) {

    suspend fun refreshAnimeList() {
        try {
            // fetch from API
            val response = api.getTopAnime()
            Log.d("Ashutoshhh", "refreshAnimeList: " + response.data)

            // save to DB
            try {
                dao.insertAllAnime(response.data)
                Log.d("RoomDB", "Data inserted successfully")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("RoomDB", "Failed to insert data: ${e.localizedMessage}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getLocalAnimeList(): List<AnimeResponse> {
        return dao.getAllAnime()
    }
}
