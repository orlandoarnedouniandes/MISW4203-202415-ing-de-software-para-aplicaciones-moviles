package com.mobileapp.mymobileapp.network

import com.mobileapp.mymobileapp.models.CollectorEntity
import retrofit2.http.GET

interface CollectorsApi {
    @GET("collectors")
    suspend fun getCollectors(): List<CollectorEntity>
}