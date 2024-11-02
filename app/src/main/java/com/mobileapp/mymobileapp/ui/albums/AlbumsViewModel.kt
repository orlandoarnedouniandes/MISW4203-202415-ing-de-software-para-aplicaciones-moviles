package com.mobileapp.mymobileapp.ui.albums

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mobileapp.mymobileapp.data.repositories.AlbumRepository
import com.mobileapp.mymobileapp.models.Album
import kotlinx.coroutines.launch

class AlbumsViewModel(private val repository: AlbumRepository) : ViewModel() {

    val albums = MutableLiveData<List<Album>>()

    fun fetchAlbums() {
        viewModelScope.launch {
            try {
                val albumList = repository.getAlbums()
                albums.postValue(albumList)
            } catch (e: Exception) {
                // Handle errors appropriately
            }
        }
    }
}

class AlbumsViewModelFactory(private val repository: AlbumRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlbumsViewModel::class.java)) {
            return AlbumsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}