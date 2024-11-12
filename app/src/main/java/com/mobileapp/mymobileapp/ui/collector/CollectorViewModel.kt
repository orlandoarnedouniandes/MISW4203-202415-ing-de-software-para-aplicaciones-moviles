package com.mobileapp.mymobileapp.ui.collector

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mobileapp.mymobileapp.data.repositories.CollectorRepository
import com.mobileapp.mymobileapp.models.Collector
import kotlinx.coroutines.launch

class CollectorViewModel(
    application: Application,
    private val repository: CollectorRepository
) : AndroidViewModel(application) {

    private val _collectors = MutableLiveData<List<Collector>>()
    val collectors: LiveData<List<Collector>> get() = _collectors

    init {
        loadMoreCollectors()
    }

    private fun loadMoreCollectors() {
        viewModelScope.launch {
            try {
                val response = repository.getCollectors()
                val currentCollectors = _collectors.value.orEmpty().toMutableList()
                currentCollectors.addAll(response)
                _collectors.value = currentCollectors
            } catch (e: Exception) {
                Log.e("CollectorViewModel", "Error loading collectors", e)
            }
        }
    }
}