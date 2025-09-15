package com.ashutosh.animeproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.ashutosh.animeproject.ApiService.RetrofitClient
import com.ashutosh.animeproject.Dao.AnimeDatabase
import com.ashutosh.animeproject.Dao.HomeViewModelFactory
import com.ashutosh.animeproject.repository.AnimeRepository
import com.ashutosh.animeproject.view.DetailScreen
import com.ashutosh.animeproject.view.HomeScreen
import com.ashutosh.animeproject.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(
            applicationContext,
            AnimeDatabase::class.java,
            "anime_db"
        ).build()
        setContent {
            val navController = rememberNavController()
            val repository = AnimeRepository(RetrofitClient.api, db.animeDao())

            val factory = HomeViewModelFactory(
                RetrofitClient.api,
                repository,
                applicationContext // or requireContext() if in Fragment
            )

            val homeViewModel: HomeViewModel = ViewModelProvider(this, factory)
                .get(HomeViewModel::class.java)

            AnimeNavHost(navController, homeViewModel)
        }
    }
}

@Composable
private fun AnimeNavHost(
    navController: androidx.navigation.NavHostController,
    homeViewModel: HomeViewModel
) {
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
