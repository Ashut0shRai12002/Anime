package com.ashutosh.animeproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ashutosh.animeproject.ApiService.ApiService
import com.ashutosh.animeproject.Dao.AnimeDatabase

import com.ashutosh.animeproject.Dao.HomeViewModelFactory
import com.ashutosh.animeproject.repository.AnimeRepository
import com.ashutosh.animeproject.view.DetailScreen
import com.ashutosh.animeproject.view.HomeScreen

import com.ashutosh.animeproject.viewmodel.HomeViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.room.Room

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Room DB
        val db = Room.databaseBuilder(
            applicationContext,
            AnimeDatabase::class.java, "database-name"
        ).build()

        val dao = db.animeDao()

        val api = Retrofit.Builder()
            .baseUrl("https://api.jikan.moe/v4/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)


        val repository = AnimeRepository(api, dao)


        setContent {
            val navController = rememberNavController()
            val factory = HomeViewModelFactory(api, repository, this)
            val homeViewModel: HomeViewModel = viewModel(factory = factory)

            NavHost(navController, startDestination = "home") {
                composable("home") {
                    HomeScreen(
                        viewModel = homeViewModel,
                        onAnimeClick = { anime ->
                            navController.navigate("detail/${anime.mal_id}")
                        }
                    )
                }
                composable("detail/{id}") { backStackEntry ->
                    val animeId = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                    animeId?.let { DetailScreen(it, navController) }
                }
            }
        }
    }
}
