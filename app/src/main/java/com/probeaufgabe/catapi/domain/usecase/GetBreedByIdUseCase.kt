package com.probeaufgabe.catapi.domain.usecase

import com.probeaufgabe.catapi.domain.model.Breed
import com.probeaufgabe.catapi.domain.repository.BreedRepository
import kotlinx.coroutines.flow.Flow

class GetBreedByIdUseCase(private val repository: BreedRepository) {
    operator fun invoke(id: String): Flow<Breed?> {
        return repository.getBreedById(id)
    }
}