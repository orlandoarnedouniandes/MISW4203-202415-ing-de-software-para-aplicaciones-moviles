package com.mobileapp.mymobileapp.network

import com.mobileapp.mymobileapp.models.Album
import retrofit2.http.GET

interface AlbumsApi {
    @GET("albums")
    suspend fun getAlbums(): List<Album>
}