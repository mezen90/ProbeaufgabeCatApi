package com.probeaufgabe.catapi.domain.usecase

import androidx.paging.PagingData
import com.probeaufgabe.catapi.domain.model.Breed
import com.probeaufgabe.catapi.domain.repository.BreedRepository
import kotlinx.coroutines.flow.Flow

class GetPagedBreedsUseCase(private val repository: BreedRepository) {
    operator fun invoke(sortByWeight: Boolean): Flow<PagingData<Breed>> {
        return repository.getPagedBreeds(sortByWeight)
    }
}