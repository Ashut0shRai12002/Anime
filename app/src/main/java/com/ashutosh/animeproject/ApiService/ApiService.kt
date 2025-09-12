package com.ashutosh.animeproject.ApiService

import com.ashutosh.animeproject.data.AnimeResponse
import com.ashutosh.animeproject.data.TopAnimeResponse
import com.ashutosh.animeproject.data.animeDetail.AnimeDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {
    @GET("top/anime")
    suspend fun getTopAnime(): TopAnimeResponse


    @GET("anime/{id}")
    suspend fun getAnimeDetails(@Path("id") id: Int): AnimeDetail
}