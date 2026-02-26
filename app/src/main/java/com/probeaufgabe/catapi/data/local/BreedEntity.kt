package com.probeaufgabe.catapi.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.probeaufgabe.catapi.domain.model.Breed

@Entity(tableName = "breeds")
data class BreedEntity(
    @PrimaryKey val id: String,
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

fun BreedEntity.toDomain(): Breed {
    return Breed(
        id = this.id,
        name = this.name,
        description = this.description,
        origin = this.origin,
        temperament = this.temperament,
        imageUrl = this.imageUrl,
        weightImperial = this.weightImperial,
        weightMetric = this.weightMetric,
        averageWeightMetric = this.averageWeightMetric,
        isFavorite = this.isFavorite,
        voteScore = this.voteScore
    )
}