package com.ashutosh.animeproject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.ashutosh.animeproject.ApiService.RetrofitClient
import com.ashutosh.animeproject.Dao.AnimeDatabase
import com.ashutosh.animeproject.data.AnimeResponse
import com.ashutosh.animeproject.repository.AnimeRepository
import com.ashutosh.animeproject.view.DetailScreen
import com.ashutosh.animeproject.view.HomeScreen
import com.ashutosh.animeproject.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val animeList = mutableStateListOf<AnimeResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val navController = rememberNavController()

            NavHost(navController, startDestination = "home") {
                composable("home") {
                    HomeScreen(onAnimeClick = { anime ->
                        navController.navigate("detail/${anime.mal_id}")
                    })
                }

                composable("detail/{id}") { backStackEntry ->
                    val animeId = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                    animeId?.let { DetailScreen(it , navController) }
                }
            }

        }

        fetchTopAnime()
    }

    private fun fetchTopAnime() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.getTopAnime()
                response.data.forEach(){
                    Log.d("Ashutoshh", "fetchTopAnime: " )
                }
                animeList.clear()
                animeList.addAll(response.data)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
