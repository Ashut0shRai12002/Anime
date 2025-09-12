package com.ashutosh.animeproject.view

import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.ashutosh.animeproject.ApiService.RetrofitClient
import com.ashutosh.animeproject.data.AnimeResponse
import com.ashutosh.animeproject.data.animeDetail.AnimeDetail
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    animeId: Int,
    navController: NavController // 👈 pass navController to handle back
) {
    var anime by remember { mutableStateOf<com.ashutosh.animeproject.data.animeDetail.Data?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(animeId) {
        try {
            val response = RetrofitClient.api.getAnimeDetails(animeId)
            anime = response.data
        } catch (e: Exception) {
            errorMessage = e.message ?: "Failed to load details"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = anime?.title_english ?: "Anime Detail") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        when {
            errorMessage != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(errorMessage!!, style = MaterialTheme.typography.bodyMedium)
                }
            }

            anime != null -> {
                val data = anime!!
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    // 🎥 Trailer or Poster
                    if (!data.trailer.youtube_id.isNullOrBlank()) {
                        YouTubeTrailer(youtubeId = data.trailer.youtube_id)
                    } else {
                        Image(
                            painter = rememberAsyncImagePainter(data.images.jpg.large_image_url),
                            contentDescription = data.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(Modifier.height(12.dp))
                    if (data.title_japanese.isNotBlank()) {
                        Text(text = data.title_japanese, style = MaterialTheme.typography.bodySmall)
                    }

                    Spacer(Modifier.height(8.dp))
                    Text("Episodes: ${data.episodes}", style = MaterialTheme.typography.bodyMedium)
                    Text("Rating: ${data.rating}", style = MaterialTheme.typography.bodyMedium)
                    Text("Year: ${data.year}", style = MaterialTheme.typography.bodyMedium)
                    Text("Score: ${data.score}", style = MaterialTheme.typography.bodyMedium)

                    Spacer(Modifier.height(8.dp))

                    val genres = (data.genres.map { it.name } +
                            data.themes.map { it.name } +
                            data.demographics.map { it.name })
                        .distinct()
                        .joinToString(", ")

                    if (genres.isNotBlank()) {
                        Text("Genres: $genres", style = MaterialTheme.typography.bodyMedium)
                    }

                    Spacer(Modifier.height(12.dp))
                    Text("Synopsis", style = MaterialTheme.typography.titleMedium)
                    Text(data.synopsis, style = MaterialTheme.typography.bodySmall)
                }
            }

            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Loading...", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
@Composable
fun YouTubeTrailer(youtubeId: String) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        factory = {
            YouTubePlayerView(context).apply {
                enableAutomaticInitialization = false

                initialize(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        // Load video when player is ready
                        youTubePlayer.loadVideo(youtubeId, 0f)
                    }
                }, true)

                // Bind lifecycle so video pauses/resumes properly
                lifecycleOwner.lifecycle.addObserver(this)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        update = {
            // Ensures lifecycle is respected during recompositions
            lifecycleOwner.lifecycle.addObserver(it)
        }
    )
}
