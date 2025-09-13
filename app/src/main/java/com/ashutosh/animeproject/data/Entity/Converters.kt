package com.ashutosh.animeproject.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    // ----- Demographic -----
    @TypeConverter
    fun fromDemographicList(list: List<Demographic>?): String {
        return gson.toJson(list)
    }


    @TypeConverter
    fun toDemographicList(data: String?): List<Demographic> {
        if (data.isNullOrEmpty()) return emptyList()
        val listType = object : TypeToken<List<Demographic>>() {}.type
        return gson.fromJson(data, listType)
    }
    // ----- Images -----
    @TypeConverter
    fun fromImages(images: Images?): String {
        return Gson().toJson(images)
    }

    @TypeConverter
    fun toImages(data: String?): Images? {
        if (data.isNullOrEmpty()) return null
        return Gson().fromJson(data, Images::class.java)
    }
    // ----- ImagesX -----
    @TypeConverter
    fun fromImagesX(images: ImagesX?): String {
        return Gson().toJson(images)
    }

    @TypeConverter
    fun toImagesX(data: String?): ImagesX? {
        if (data.isNullOrEmpty()) return null
        return Gson().fromJson(data, ImagesX::class.java)
    }


    // ----- Jpg -----
    @TypeConverter
    fun fromJpg(jpg: Jpg?): String {
        return Gson().toJson(jpg)
    }

    @TypeConverter
    fun toJpg(data: String?): Jpg? {
        if (data.isNullOrEmpty()) return null
        return Gson().fromJson(data, Jpg::class.java)
    }

    // ----- Any? List -----
    @TypeConverter
    fun fromAnyList(list: List<Any?>?): String = gson.toJson(list)

    @TypeConverter
    fun toAnyList(data: String?): List<Any?> {
        if (data.isNullOrEmpty()) return emptyList()
        val type = object : TypeToken<List<Any?>>() {}.type
        return gson.fromJson(data, type)
    }

    // ----- Webp -----
    @TypeConverter
    fun fromWebp(webp: Webp?): String {
        return Gson().toJson(webp)
    }

    @TypeConverter
    fun toWebp(data: String?): Webp? {
        if (data.isNullOrEmpty()) return null
        return Gson().fromJson(data, Webp::class.java)
    }

    // ----- Trailer -----
    @TypeConverter
    fun fromTrailer(trailer: Trailer?): String {
        return Gson().toJson(trailer)
    }

    @TypeConverter
    fun toTrailer(data: String?): Trailer? {
        if (data.isNullOrEmpty()) return null
        return Gson().fromJson(data, Trailer::class.java)
    }



    // ----- Genre -----
    @TypeConverter
    fun fromGenreList(list: List<Genre>?): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toGenreList(data: String?): List<Genre> {
        if (data.isNullOrEmpty()) return emptyList()
        val listType = object : TypeToken<List<Genre>>() {}.type
        return gson.fromJson(data, listType)
    }

    // ----- Licensor -----
    @TypeConverter
    fun fromLicensorList(list: List<Licensor>?): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toLicensorList(data: String?): List<Licensor> {
        if (data.isNullOrEmpty()) return emptyList()
        val listType = object : TypeToken<List<Licensor>>() {}.type
        return gson.fromJson(data, listType)
    }

    // ----- Producer -----
    @TypeConverter
    fun fromProducerList(list: List<Producer>?): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toProducerList(data: String?): List<Producer> {
        if (data.isNullOrEmpty()) return emptyList()
        val listType = object : TypeToken<List<Producer>>() {}.type
        return gson.fromJson(data, listType)
    }

    // ----- Studio -----
    @TypeConverter
    fun fromStudioList(list: List<Studio>?): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toStudioList(data: String?): List<Studio> {
        if (data.isNullOrEmpty()) return emptyList()
        val listType = object : TypeToken<List<Studio>>() {}.type
        return gson.fromJson(data, listType)
    }

    // ----- Theme -----
    @TypeConverter
    fun fromThemeList(list: List<Theme>?): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toThemeList(data: String?): List<Theme> {
        if (data.isNullOrEmpty()) return emptyList()
        val listType = object : TypeToken<List<Theme>>() {}.type
        return gson.fromJson(data, listType)
    }

    // ----- Title -----
    @TypeConverter
    fun fromTitleList(list: List<Title>?): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toTitleList(data: String?): List<Title> {
        if (data.isNullOrEmpty()) return emptyList()
        val listType = object : TypeToken<List<Title>>() {}.type
        return gson.fromJson(data, listType)
    }

    // ----- Title synonyms (List<String>) -----
    @TypeConverter
    fun fromStringList(list: List<String>?): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toStringList(data: String?): List<String> {
        if (data.isNullOrEmpty()) return emptyList()
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(data, listType)
    }

    // ----- Prop -----
    @TypeConverter
    fun fromProp(prop: Prop?): String {
        return gson.toJson(prop)
    }

    @TypeConverter
    fun toProp(data: String?): Prop? {
        if (data.isNullOrEmpty()) return null
        return gson.fromJson(data, Prop::class.java)
    }
}
