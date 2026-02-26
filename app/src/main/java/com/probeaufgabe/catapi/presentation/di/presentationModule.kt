package com.probeaufgabe.catapi.presentation.di

import com.probeaufgabe.catapi.presentation.breeds.BreedViewModel
import com.probeaufgabe.catapi.presentation.detail.BreedDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        BreedViewModel(
            getPagedBreedsUseCase = get(),
            updateVoteUseCase = get()
        )
    }
    viewModel {
        BreedDetailViewModel(
            savedStateHandle = get(),
            getBreedByIdUseCase = get(),
            updateVoteUseCase = get(),
        )
    }
}