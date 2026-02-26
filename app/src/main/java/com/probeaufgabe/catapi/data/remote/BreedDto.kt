package com.probeaufgabe.catapi.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BreedDto(
    val id: String,
    val name: String,
    val description: String? = null,
    val origin: String? = null,
    val temperament: String? = null,
    val weight: WeightDto? = null,
    @SerialName("reference_image_id") val referenceImageId: String? = null,
    val image: ImageDto? = null
)