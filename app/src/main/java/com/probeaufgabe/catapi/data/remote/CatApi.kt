package com.probeaufgabe.catapi.data.remote

import com.probeaufgabe.catapi.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CatApi {
    @GET("breeds")
    suspend fun getBreeds(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Header("x-api-key") apiKey: String = BuildConfig.CAT_API_KEY

    ): List<BreedDto>
}