package com.mobileapp.mymobileapp.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mobileapp.mymobileapp.models.CollectorAlbum
import com.mobileapp.mymobileapp.models.Comment
import com.mobileapp.mymobileapp.models.Performer

class Converters {

    @TypeConverter
    fun fromCollectorAlbumList(value: List<CollectorAlbum>): String {
        val gson = Gson()
        val type = object : TypeToken<List<CollectorAlbum>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCollectorAlbumList(value: String): List<CollectorAlbum> {
        val gson = Gson()
        val type = object : TypeToken<List<CollectorAlbum>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromCollectorToPerformersList(value: List<Performer>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Performer>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCollectorToPerformersList(value: String): List<Performer> {
        val gson = Gson()
        val type = object : TypeToken<List<Performer>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromCollectorToCommentsList(value: List<Comment>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Comment>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCollectorToCommentsList(value: String): List<Comment> {
        val gson = Gson()
        val type = object : TypeToken<List<Comment>>() {}.type
        return gson.fromJson(value, type)
    }

}