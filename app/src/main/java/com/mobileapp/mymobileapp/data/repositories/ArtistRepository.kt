package com.mobileapp.mymobileapp.data.repositories

import com.mobileapp.mymobileapp.data.dao.ArtistDao
import com.mobileapp.mymobileapp.models.Artist
import com.mobileapp.mymobileapp.models.ArtistEntity
import com.mobileapp.mymobileapp.network.ArtistsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArtistRepository(private val api: ArtistsApi, private val artistDao: ArtistDao) {

    suspend fun getArtists(): List<Artist> {
        return withContext(Dispatchers.IO) {
            try {
                val artistsFromNetwork = api.getArtists()

                artistDao.insertAll(artistsFromNetwork.map { artist ->
                    ArtistEntity(
                        id = artist.id,
                        name = artist.name,
                        image = artist.image,
                        description = artist.description,
                        birthDate = artist.birthDate
                    )
                })

                artistDao.getAllArtists().map { entity ->
                    Artist(
                        id = entity.id,
                        name = entity.name,
                        image = entity.image,
                        description = entity.description,
                        birthDate = entity.birthDate,
                        albums = emptyList(),
                        performerPrizes = emptyList()
                    )
                }
            } catch (e: Exception) {
                artistDao.getAllArtists().map { entity ->
                    Artist(
                        id = entity.id,
                        name = entity.name,
                        image = entity.image,
                        description = entity.description,
                        birthDate = entity.birthDate,
                        albums = emptyList(),
                        performerPrizes = emptyList()
                    )
                }
            }
        }
    }
}