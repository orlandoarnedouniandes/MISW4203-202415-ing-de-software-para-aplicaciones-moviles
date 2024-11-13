package com.mobileapp.mymobileapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artists")
data class ArtistEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val date: String
)