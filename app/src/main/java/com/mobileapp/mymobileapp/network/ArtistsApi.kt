package com.mobileapp.mymobileapp.network

import com.mobileapp.mymobileapp.models.Artist
import com.mobileapp.mymobileapp.models.Bands
import retrofit2.http.GET

interface ArtistsApi {
    @GET("musicians")
    suspend fun getArtists(): List<Artist>
    @GET("bands")
    suspend fun getBands(): List<Bands>
}