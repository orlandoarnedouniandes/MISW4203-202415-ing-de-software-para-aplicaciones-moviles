package com.mobileapp.mymobileapp.ui.collector

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobileapp.mymobileapp.data.repositories.CollectorRepository

class CollectorViewModelFactory(
    private val application: Application,
    private val repository: CollectorRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CollectorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CollectorViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}