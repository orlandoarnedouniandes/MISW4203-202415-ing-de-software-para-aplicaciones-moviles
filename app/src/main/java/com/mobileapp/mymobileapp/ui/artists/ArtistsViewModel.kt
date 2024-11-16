package com.mobileapp.mymobileapp.ui.artists

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mobileapp.mymobileapp.data.repositories.ArtistRepository
import com.mobileapp.mymobileapp.models.Artist
import com.mobileapp.mymobileapp.network.ArtistsApi
import kotlinx.coroutines.launch

class ArtistsViewModel(private val api: ArtistRepository) : ViewModel() {

    private val _artists = MutableLiveData<List<Artist>>()
    val artists: LiveData<List<Artist>> get()= _artists

    init {
        fetchArtists()
    }

    private fun fetchArtists() {
        viewModelScope.launch {
            try {
                val fetchedArtists = api.getArtists()
                val currentArtists = _artists.value.orEmpty().toMutableList()
                currentArtists.addAll(fetchedArtists)
                _artists.value = currentArtists
            } catch (e: Exception) {
                Log.e("ArtistsViewModel", "Error fetching artists", e)
            }
        }
    }
}

class ArtistsViewModelFactory(private val repository: ArtistRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArtistsViewModel::class.java)) {
            return ArtistsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}