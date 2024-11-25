package com.mobileapp.mymobileapp.network

import com.mobileapp.mymobileapp.models.Album
import com.mobileapp.mymobileapp.models.CreateAlbum
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AlbumsApi {
    @GET("albums")
    suspend fun getAlbums(): List<Album>

    @POST("/albums")
    suspend fun createAlbum(@Body album: CreateAlbum): Response<CreateAlbum>
}