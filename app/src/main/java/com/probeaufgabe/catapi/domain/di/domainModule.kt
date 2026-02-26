package com.probeaufgabe.catapi.domain.di

import com.probeaufgabe.catapi.domain.usecase.GetBreedByIdUseCase
import com.probeaufgabe.catapi.domain.usecase.GetPagedBreedsUseCase
import com.probeaufgabe.catapi.domain.usecase.UpdateVoteUseCase
import org.koin.dsl.module

val domainModule = module {

    factory { GetPagedBreedsUseCase(get()) }
    factory { GetBreedByIdUseCase(get()) }
    factory { UpdateVoteUseCase(get()) }
}