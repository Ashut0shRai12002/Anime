package com.ashutosh.animeproject.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashutosh.animeproject.data.AnimeResponse
import com.ashutosh.animeproject.repository.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.ashutosh.animeproject.ApiService.ApiService

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}
sealed class UiState {
    object Loading : UiState()
    data class Success(val animeList: List<AnimeResponse>) : UiState()
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
            try {
                val localList = repository.getLocalAnimeList()
                Log.d("Ashutoshhh", " local Db " + localList)

                if (!isInternetAvailable(context)) {
                    // No internet, show cached data
                    if (localList.isNotEmpty()) {
                        _uiState.value = UiState.Success(localList)
                    } else {
                        _uiState.value = UiState.Error("No internet and no cached data")
                    }
                } else {
                    // Internet is available, fetch from API
                    repository.refreshAnimeList()
                    val response  = api.getTopAnime()


                    _uiState.value = UiState.Success(response.data)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.value = UiState.Error("Failed to load anime")
            }
        }
    }
}
