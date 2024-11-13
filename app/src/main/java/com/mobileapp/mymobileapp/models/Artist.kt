package com.mobileapp.mymobileapp.models

data class Artist(
    val birthDate: String,
    val albums: List<Album>,
    val performerPrizes: List<PerformerPrize>,
    val id: Int,
    val name: String,
    val image: String,
    val description: String
)

data class Bands(
    val creationDate: String,
    val albums: List<Album>,
    val performerPrizes: List<PerformerPrize>,
    val id: Int,
    val name: String,
    val image: String,
    val description: String
)


data class PerformerPrize(
    val id: Int,
    val premiationDate: String
)