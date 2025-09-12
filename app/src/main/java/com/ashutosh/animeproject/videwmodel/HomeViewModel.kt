package com.ashutosh.animeproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashutosh.animeproject.data.Entity.AnimeEntity
import com.ashutosh.animeproject.repository.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class UiState {
    object Loading : UiState()
    data class Success(val animeList: List<AnimeEntity>) : UiState()
    data class Error(val message: String) : UiState()
}

class HomeViewModel(private val repository: AnimeRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    fun fetchTopAnime() {
        viewModelScope.launch {
            repository.getTopAnime().collect { list ->
                _uiState.value = UiState.Success(list)
            }
        }
    }
}
