package com.ashutosh.animeproject.Dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ashutosh.animeproject.data.AnimeResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnime(anime: AnimeResponse)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAnime(animeList: List<AnimeResponse>)

    @Query("SELECT * FROM anime_table")
    suspend fun getAllAnime(): List<AnimeResponse>

    @Query("SELECT * FROM anime_table WHERE mal_id = :id")
    suspend fun getAnimeById(id: Int): AnimeResponse?
}
