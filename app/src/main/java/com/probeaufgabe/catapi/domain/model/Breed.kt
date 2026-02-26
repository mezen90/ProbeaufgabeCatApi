package com.probeaufgabe.catapi.domain.model

data class Breed(
    val id: String,
    val name: String,
    val description: String,
    val origin: String,
    val temperament: String,
    val imageUrl: String?,
    val weightImperial: String,
    val weightMetric: String,
    val averageWeightMetric: Double,
    val isFavorite: Boolean = false,
    val voteScore: Int = 0
)