package com.mobileapp.mymobileapp.data.repositories

import android.util.Log
import com.mobileapp.mymobileapp.data.dao.AlbumDao
import com.mobileapp.mymobileapp.models.Album
import com.mobileapp.mymobileapp.models.AlbumEntity
import com.mobileapp.mymobileapp.network.AlbumsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlbumRepository(private val api: AlbumsApi, private val albumDao: AlbumDao) {

    suspend fun getAlbums(): List<Album> {
        return withContext(Dispatchers.IO) {
            try {
                val albumsFromNetwork = api.getAlbums()
                Log.d("AlbumRepository", "Fetched ${albumsFromNetwork.size} albums from the network")

                // Insert the network albums into the database
                albumDao.insertAll(albumsFromNetwork.map { album ->
                    AlbumEntity(
                        album.id,
                        album.name,
                        album.cover,
                        album.releaseDate,
                        album.description,
                        album.genre,
                        album.recordLabel
                    )
                })
                albumsFromNetwork // Return the list
            } catch (e: Exception) {
                Log.e("AlbumRepository", "Error fetching albums", e)
                emptyList() // Return an empty list on failure
            }
        }
    }
}