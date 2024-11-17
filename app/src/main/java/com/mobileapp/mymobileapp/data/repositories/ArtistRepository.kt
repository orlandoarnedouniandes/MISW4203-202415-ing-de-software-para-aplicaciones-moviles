package com.mobileapp.mymobileapp.data.repositories

import android.util.Log
import com.mobileapp.mymobileapp.data.dao.ArtistDao
import com.mobileapp.mymobileapp.models.Artist
import com.mobileapp.mymobileapp.models.ArtistEntity
import com.mobileapp.mymobileapp.network.ArtistsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class ArtistRepository(private val api: ArtistsApi, private val artistDao: ArtistDao) {

    suspend fun getArtists(): List<Artist> {
        return withContext(Dispatchers.IO) {
            try {
                // Perform both network calls concurrently
                val artistsDeferred = async { api.getArtists() }
                val bandsDeferred = async { api.getBands() }

                // Wait for both network calls to complete
                val artistsFromNetwork = artistsDeferred.await()
                val bandsFromNetwork = bandsDeferred.await()

                // Insert both artists and bands in a single transaction if possible (depends on DB)
                val combinedArtists = mutableListOf<ArtistEntity>().apply {
                    addAll(artistsFromNetwork.map { artist ->
                        ArtistEntity(
                            id = artist.id,
                            name = artist.name,
                            image = artist.image,
                            description = artist.description,
                            date = artist.birthDate
                        )
                    })
                    addAll(bandsFromNetwork.map { band ->
                        ArtistEntity(
                            id = band.id,
                            name = band.name,
                            image = band.image,
                            description = band.description,
                            date = band.creationDate
                        )
                    })
                }

                // Insert all artists and bands into the database in a single operation
                artistDao.insertAll(combinedArtists)

                // Retrieve all artists from the database and map to Artist model
                artistDao.getAllArtists().map { entity -> entity.toArtist() }
            } catch (e: Exception) {
                Log.e("ArtistRepository", "Error fetching artists or bands", e)
                // Return cached artists in case of an error
                artistDao.getAllArtists().map { entity -> entity.toArtist() }
            }
        }
    }

    private fun ArtistEntity.toArtist(): Artist {
        return Artist(
            id = this.id,
            name = this.name,
            image = this.image,
            description = this.description,
            albums = emptyList(),
            performerPrizes = emptyList(),
            birthDate = this.date
        )
    }
}