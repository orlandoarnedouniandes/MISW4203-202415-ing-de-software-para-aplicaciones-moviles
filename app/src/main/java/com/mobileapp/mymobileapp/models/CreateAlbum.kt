package com.mobileapp.mymobileapp.models

data class CreateAlbum(
    val name: String,
    val cover: String,
    val releaseDate: String,
    val description: String,
    val genre: String,
    val recordLabel: String
)