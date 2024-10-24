package com.mobileapp.mymobileapp.network

import com.mobileapp.mymobileapp.models.Artist
import retrofit2.http.GET

interface ArtistsApi {
    @GET("musicians")
    suspend fun getArtists(): List<Artist>
}