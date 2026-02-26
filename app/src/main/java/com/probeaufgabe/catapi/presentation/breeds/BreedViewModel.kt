package com.probeaufgabe.catapi.presentation.breeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.probeaufgabe.catapi.domain.model.Breed
import com.probeaufgabe.catapi.domain.usecase.GetPagedBreedsUseCase
import com.probeaufgabe.catapi.domain.usecase.UpdateVoteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class BreedViewModel(
    private val getPagedBreedsUseCase: GetPagedBreedsUseCase,
    private val updateVoteUseCase: UpdateVoteUseCase,
) : ViewModel() {

    private val _sortByWeight = MutableStateFlow(false)
    val sortByWeight = _sortByWeight.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val breeds: Flow<PagingData<Breed>> = _sortByWeight.flatMapLatest { isSorted ->
        getPagedBreedsUseCase(isSorted)
    }.cachedIn(viewModelScope)

    fun toggleSort() {
        _sortByWeight.value = !_sortByWeight.value
    }

    fun onVote(breedId: String, voteChange: Int) {
        viewModelScope.launch {
            updateVoteUseCase(breedId, voteChange)
        }
    }

    fun onToggleFavorite() {
        //TODO
    }

}