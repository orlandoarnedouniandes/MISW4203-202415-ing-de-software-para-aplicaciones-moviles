package com.mobileapp.mymobileapp.ui.collectionists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CollectionistsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Collectionists Fragment"
    }
    val text: LiveData<String> = _text
}