package com.ashutosh.animeproject.Dao

import com.ashutosh.animeproject.data.Entity.AnimeEntity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {
    @Query("SELECT * FROM anime_table")
    fun getAllAnime(): Flow<List<AnimeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(animeList: List<AnimeEntity>)

    @Query("DELETE FROM anime_table")
    suspend fun clearAll()
}
