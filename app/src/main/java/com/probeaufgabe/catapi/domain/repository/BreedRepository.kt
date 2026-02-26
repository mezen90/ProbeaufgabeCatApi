package com.probeaufgabe.catapi.domain.repository

import androidx.paging.PagingData
import com.probeaufgabe.catapi.domain.model.Breed
import kotlinx.coroutines.flow.Flow

interface BreedRepository {
    fun getPagedBreeds(sortByWeight: Boolean): Flow<PagingData<Breed>>

    fun getBreedById(id: String): Flow<Breed?>

    suspend fun voteBreed(breedId: String, voteChange: Int)
}