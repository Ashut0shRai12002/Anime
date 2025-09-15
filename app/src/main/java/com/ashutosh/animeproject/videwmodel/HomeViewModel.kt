package com.ashutosh.animeproject.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashutosh.animeproject.ApiService.ApiService
import com.ashutosh.animeproject.data.Entity.AnimeUiModel
import com.ashutosh.animeproject.data.Entity.toUiModel
import com.ashutosh.animeproject.repository.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

// Check internet availability
fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

// UI state sealed class
sealed class UiState {
    object Loading : UiState()
    data class Success(val animeList: List<AnimeUiModel>) : UiState()
    data class Error(val message: String) : UiState()
}

class HomeViewModel(
    private val api: ApiService,
    private val repository: AnimeRepository,
    private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        viewModelScope.launch {
            observeLocalAnime() // Step 1: observe DB
            fetchFromApiIfOnline() // Step 2: fetch from API if online
        }
    }

    // Step 1: Observe cached anime from DB
    private fun observeLocalAnime() {
        viewModelScope.launch {
            repository.getCachedAnime().collect { localList ->
                if (localList.isNotEmpty()) {
                    _uiState.value = UiState.Success(localList.map { it.toUiModel() })
                    Log.d("HomeViewModel", "Showing cached anime")
                } else {
                    _uiState.value = UiState.Loading
                }
            }
        }
    }

    // Step 2: Fetch from API only if internet is available
    private fun fetchFromApiIfOnline() {
        if (!isInternetAvailable(context)) return

        viewModelScope.launch {
            try {
                repository.refreshAnimeList()
                Log.d("HomeViewModel", "Data synced from API")
            } catch (e: HttpException) {
                if (e.code() == 429) {
                    _uiState.value = UiState.Error("Rate limit reached. Try again later.")
                } else {
                    _uiState.value = UiState.Error("Failed to fetch anime: ${e.message()}")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Unexpected error: ${e.localizedMessage}")
            }
        }
    }
}
