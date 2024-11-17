package com.mobileapp.mymobileapp.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.mobileapp.mymobileapp.database.CollectorDatabase
import com.mobileapp.mymobileapp.models.Collector
import com.mobileapp.mymobileapp.models.CollectorEntity
import com.mobileapp.mymobileapp.network.CollectorsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CollectorRepository(
    private val collectorsApi: CollectorsApi,
    private val database: CollectorDatabase
) {

    val collectors: LiveData<List<CollectorEntity>> = database.collectorDao().getAllCollectors()

    suspend fun getCollectors(): List<Collector> {
        return withContext(Dispatchers.IO) {
            try {
                val apiCollectors = collectorsApi.getCollectors()
                database.collectorDao().insertAll(apiCollectors)
                apiCollectors.map { it.toCollector() }
            } catch (e: Exception) {
                Log.e("CollectorRepository", "Error fetching collectors", e)
                // If network call fails, fallback to database cache
                database.collectorDao().getAllCollectors().value?.map { it.toCollector() } ?: emptyList()
            }
        }
    }

    private fun CollectorEntity.toCollector(): Collector {
        return Collector(
            id = id,
            name = name,
            email = email,
            telephone = telephone,
            comments = comments,
            collectorAlbums = collectorAlbums,
            favoritePerformers = favoritePerformers
        )
    }
}