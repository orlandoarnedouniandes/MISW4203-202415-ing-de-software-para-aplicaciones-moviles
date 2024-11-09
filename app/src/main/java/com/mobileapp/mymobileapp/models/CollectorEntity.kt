package com.mobileapp.mymobileapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collectors")
data class CollectorEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val telephone: String,
    val email: String,
    val comments: List<Comment>,
    val favoritePerformers: List<Performer>,
    val collectorAlbums: List<CollectorAlbum>
)





