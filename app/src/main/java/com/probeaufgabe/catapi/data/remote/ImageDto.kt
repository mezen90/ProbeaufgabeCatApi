package com.probeaufgabe.catapi.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class ImageDto(
    val id: String? = null,
    val url: String? = null
)