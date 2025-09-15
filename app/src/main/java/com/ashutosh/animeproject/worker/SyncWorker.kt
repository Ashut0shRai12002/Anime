package com.ashutosh.animeproject.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ashutosh.animeproject.ApiService.RetrofitClient
import com.ashutosh.animeproject.Dao.AnimeDatabase
import com.ashutosh.animeproject.repository.AnimeRepository

class SyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val api = RetrofitClient.api
    private val db = AnimeDatabase.getInstance(context) // your Room DB
    private val repository = AnimeRepository(api, db.animeDao())

    override suspend fun doWork(): Result {
        return try {
            repository.refreshAnimeList() // fetch + update DB
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
