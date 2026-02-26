package com.probeaufgabe.catapi.data.mapper

import com.probeaufgabe.catapi.data.local.BreedEntity
import com.probeaufgabe.catapi.data.remote.BreedDto
import com.probeaufgabe.catapi.domain.model.Breed

fun BreedDto.toEntity(): BreedEntity {
    val calculateAverage: (String?) -> Double = { weightStr ->
        try {
            if (weightStr.isNullOrBlank()) 0.0
            else {
                val parts = weightStr.split("-").map { it.trim().toDoubleOrNull() ?: 0.0 }
                if (parts.size == 2) (parts[0] + parts[1]) / 2.0 else parts.firstOrNull() ?: 0.0
            }
        } catch (e: Exception) {
            0.0
        }
    }

    return BreedEntity(
        id = this.id,
        name = this.name,
        description = this.description ?: "No description available",
        origin = this.origin ?: "Unknown",
        temperament = this.temperament ?: "Unknown",
        imageUrl = this.image?.url, // Das Bild aus dem verschachtelten Objekt holen
        weightImperial = this.weight?.imperial ?: "?",
        weightMetric = this.weight?.metric ?: "?",
        averageWeightMetric = calculateAverage(this.weight?.metric),
    )
}

fun BreedEntity.toDomainModel(): Breed {
    return Breed(
        id = id,
        name = name,
        description = description,
        origin = origin,
        temperament = temperament,
        imageUrl = imageUrl,
        weightImperial = weightImperial,
        weightMetric = weightMetric,
        averageWeightMetric = averageWeightMetric,
        isFavorite = isFavorite,
        voteScore = voteScore
    )
}