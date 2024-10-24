package com.mobileapp.mymobileapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mobileapp.mymobileapp.models.ArtistEntity

@Dao
interface ArtistDao {

    @Query("SELECT * FROM artists")
    fun getAllArtists(): List<ArtistEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(artists: List<ArtistEntity>)
}