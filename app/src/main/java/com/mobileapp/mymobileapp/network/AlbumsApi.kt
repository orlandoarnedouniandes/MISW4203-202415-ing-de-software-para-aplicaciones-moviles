package com.mobileapp.mymobileapp.network

import com.mobileapp.mymobileapp.models.Album
import com.mobileapp.mymobileapp.models.CreateAlbum
import com.mobileapp.mymobileapp.models.Track
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AlbumsApi {
    @GET("albums")
    suspend fun getAlbums(): List<Album>

    @POST("/albums")
    suspend fun createAlbum(@Body album: CreateAlbum): Response<CreateAlbum>

    @POST("albums/{albumId}/tracks")
    suspend fun addTrackToAlbum(
        @Path("albumId") albumId: String,
        @Body track: Track
    ): Response<Unit>
}