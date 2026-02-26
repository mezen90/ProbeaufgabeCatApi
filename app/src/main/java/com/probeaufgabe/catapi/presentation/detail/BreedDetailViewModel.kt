package com.probeaufgabe.catapi.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.probeaufgabe.catapi.core.UiState
import com.probeaufgabe.catapi.domain.model.Breed
import com.probeaufgabe.catapi.domain.usecase.GetBreedByIdUseCase
import com.probeaufgabe.catapi.domain.usecase.UpdateVoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BreedDetailViewModel(
    savedStateHandle: SavedStateHandle,
    getBreedByIdUseCase: GetBreedByIdUseCase,
    private val updateVoteUseCase: UpdateVoteUseCase
) : ViewModel() {

    private val breedId: String = checkNotNull(savedStateHandle["breedId"])

    val breed: StateFlow<Breed?> = getBreedByIdUseCase(breedId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
    private val _isMetric = MutableStateFlow(true)
    val isMetric = _isMetric.asStateFlow()

    val uiState: StateFlow<UiState<Breed>> = getBreedByIdUseCase(breedId)
        .map { breed ->
            if (breed != null) {
                UiState.Success(breed)
            } else {
                UiState.Error("Katze nicht in der Datenbank gefunden.") // ID existiert nicht
            }
        }
        .onStart { emit(UiState.Loading) }
        .catch { e -> emit(UiState.Error(e.message ?: "Ein unbekannter Fehler ist aufgetreten")) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading
        )

    fun toggleWeightUnit() {
        _isMetric.value = !_isMetric.value
    }

    fun vote(change: Int) {
        viewModelScope.launch {
            updateVoteUseCase(breedId, change)
        }
    }
}
