package com.probeaufgabe.catapi.domain.usecase

import com.probeaufgabe.catapi.domain.repository.BreedRepository

class UpdateVoteUseCase(private val repository: BreedRepository) {
    suspend operator fun invoke(id: String, change: Int) {
        repository.voteBreed(id, change)
    }
}