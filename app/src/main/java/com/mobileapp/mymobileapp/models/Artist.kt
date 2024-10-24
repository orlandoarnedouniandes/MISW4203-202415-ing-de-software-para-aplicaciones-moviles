package com.mobileapp.mymobileapp.models

data class Artist(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val birthDate: String,
    val albums: List<Album>,
    val performerPrizes: List<PerformerPrize>
)

data class PerformerPrize(
    val id: Int,
    val premiationDate: String
)