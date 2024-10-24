package com.mobileapp.mymobileapp.data.repositories

import com.mobileapp.mymobileapp.data.dao.AlbumDao
import com.mobileapp.mymobileapp.models.Album
import com.mobileapp.mymobileapp.models.AlbumEntity
import com.mobileapp.mymobileapp.network.AlbumsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlbumRepository(private val api: AlbumsApi, private val albumDao: AlbumDao) {

    suspend fun getAlbums(): List<Album> {
        return withContext(Dispatchers.IO) {
            val albumsFromNetwork = api.getAlbums()

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

            // Fetch albums from the database and map them to the Album model
            albumDao.getAllAlbums().map { entity ->
                Album(
                    entity.id,
                    entity.name,
                    entity.cover,
                    entity.releaseDate,
                    entity.description,
                    entity.genre,
                    entity.recordLabel,
                    tracks = emptyList(), // Update these fields if needed
                    performers = emptyList(),
                    comments = emptyList()
                )
            }
        }
    }
}