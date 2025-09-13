package com.ashutosh.animeproject.Dao


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ashutosh.animeproject.ApiService.ApiService
import com.ashutosh.animeproject.repository.AnimeRepository
import com.ashutosh.animeproject.viewmodel.HomeViewModel

class HomeViewModelFactory(
    private val api: ApiService,
    private val repository: AnimeRepository,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(api, repository, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
