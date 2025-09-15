package com.ashutosh.animeproject.Dao


import androidx.room.Database
import androidx.room.RoomDatabase
import com.ashutosh.animeproject.data.Entity.AnimeEntity

@Database(entities = [AnimeEntity
::class], version = 1, exportSchema = false)
abstract class AnimeDatabase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao
}