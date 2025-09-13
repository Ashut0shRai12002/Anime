package com.ashutosh.animeproject.Dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ashutosh.animeproject.data.AnimeResponse
import com.ashutosh.animeproject.data.Converters

@Database(entities = [AnimeResponse::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AnimeDatabase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao
}
