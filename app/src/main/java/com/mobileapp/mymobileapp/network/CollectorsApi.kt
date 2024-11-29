package com.mobileapp.mymobileapp.network

import com.mobileapp.mymobileapp.models.CollectorAlbum
import com.mobileapp.mymobileapp.models.CollectorEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface CollectorsApi {
    @GET("collectors")
    suspend fun getCollectors(): List<CollectorEntity>

    @GET("collectors/{id}/albums")
    suspend fun getCollectorAlbums(@Path("id") collectorId: Int): List<CollectorAlbum>

}