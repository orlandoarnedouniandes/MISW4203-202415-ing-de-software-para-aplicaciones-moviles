package com.mobileapp.mymobileapp.data.repositories

import android.util.Log
import com.mobileapp.mymobileapp.data.dao.AlbumDao
import com.mobileapp.mymobileapp.models.Album
import com.mobileapp.mymobileapp.models.AlbumEntity
import com.mobileapp.mymobileapp.network.AlbumsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlbumRepository(private val api: AlbumsApi, private val albumDao: AlbumDao) {

    // Cache for albums to avoid redundant network calls
    private var cachedAlbums: List<Album> = emptyList()

    suspend fun getAlbums(): List<Album> {
        return withContext(Dispatchers.IO) {
            try {
                val localAlbums = albumDao.getAllAlbums()
                // If database is empty or cache is invalid, fetch from the network
                // We can create a more sophisticated cache invalidation strategy
                return@withContext (if (localAlbums.isEmpty()) {
                    fetchFromNetworkAndSave()
                } else {
                    // Optionally compare with the network data and decide whether to update
                    if (shouldFetchFromNetwork(localAlbums)) {
                        fetchFromNetworkAndSave()
                    } else {
                        Log.d("AlbumRepository", "Using cached albums")
                        cachedAlbums
                    }
                })
            } catch (e: Exception) {
                Log.e("AlbumRepository", "Error fetching albums", e)
                emptyList()
            }
        }
    }

    private suspend fun fetchFromNetworkAndSave(): List<Album> {
        return withContext(Dispatchers.IO) {
            try {
                val albumsFromNetwork = api.getAlbums()
                Log.d("AlbumRepository", "Fetched ${albumsFromNetwork.size} albums from the network")
                // Update database asynchronously using transaction to avoid blocking
                insertOrUpdateAlbums(albumsFromNetwork)
                cachedAlbums = albumsFromNetwork
                return@withContext albumsFromNetwork
            } catch (e: Exception) {
                Log.e("AlbumRepository", "Error fetching albums from network", e)
                return@withContext emptyList<Album>()
            }
        }
    }

    private suspend fun insertOrUpdateAlbums(albums: List<Album>) {
        val albumEntities = albums.map { album ->
            AlbumEntity(
                album.id,
                album.name,
                album.cover,
                album.releaseDate,
                album.description,
                album.genre,
                album.recordLabel
            )
        }
        // Perform batch insert asynchronously in a transaction
        albumDao.insertAll(albumEntities)
    }

    private fun shouldFetchFromNetwork(localAlbums: List<AlbumEntity>): Boolean {
        // We could compare the local data with the network data to decide whether to update
        // For simplicity, we always fetch from the network but we can add more logic here
        return true
    }
}