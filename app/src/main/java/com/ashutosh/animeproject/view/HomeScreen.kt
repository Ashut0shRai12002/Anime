package com.ashutosh.animeproject.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.ashutosh.animeproject.data.AnimeResponse
import com.ashutosh.animeproject.videwmodel.HomeViewModel
import com.ashutosh.animeproject.videwmodel.UiState

@Composable
fun HomeScreen(
    onAnimeClick: (AnimeResponse) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    when(uiState) {
        is UiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is UiState.Success -> {
            val animeList = (uiState as UiState.Success).animeList
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(animeList) { anime ->
                    AnimeItem(anime = anime, onClick = { onAnimeClick(anime) })
                }
            }
        }

        is UiState.Error -> {
            val message = (uiState as UiState.Error).message
            Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("Error: $message")
            }
        }
    }
}
@Composable
fun AnimeItem(anime: AnimeResponse, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable { onClick() }
    ) {
        Row(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            Image(
                painter = rememberAsyncImagePainter(anime.images.jpg.image_url),
                contentDescription = anime.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.width(100.dp).fillMaxHeight()
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(text = anime.title, style = MaterialTheme.typography.titleMedium)
                Text(text = "Score: ${anime.score}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
