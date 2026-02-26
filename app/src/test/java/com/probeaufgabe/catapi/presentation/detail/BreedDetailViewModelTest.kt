package com.probeaufgabe.catapi.presentation.detail

import androidx.lifecycle.SavedStateHandle
import com.probeaufgabe.catapi.core.UiState
import com.probeaufgabe.catapi.domain.model.Breed
import com.probeaufgabe.catapi.domain.usecase.GetBreedByIdUseCase
import com.probeaufgabe.catapi.domain.usecase.UpdateVoteUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BreedDetailViewModelTest {

    private lateinit var getBreedByIdUseCase: GetBreedByIdUseCase
    private lateinit var updateVoteUseCase: UpdateVoteUseCase
    private lateinit var savedStateHandle: SavedStateHandle

    private lateinit var viewModel: BreedDetailViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())

        getBreedByIdUseCase = mockk()
        updateVoteUseCase = mockk()
        savedStateHandle = SavedStateHandle(mapOf("breedId" to "cat123"))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState wird zu Success, wenn die Katze gefunden wird`() = runTest {
        val mockBreed = mockk<Breed>(relaxed = true)
        every { getBreedByIdUseCase("cat123") } returns flowOf(mockBreed)

        viewModel = BreedDetailViewModel(
            savedStateHandle = savedStateHandle,
            getBreedByIdUseCase = getBreedByIdUseCase,
            updateVoteUseCase = updateVoteUseCase
        )

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect()
        }

        advanceUntilIdle()
        val currentState = viewModel.uiState.value

        assertTrue(
            "State sollte Success sein, ist aber $currentState",
            currentState is UiState.Success
        )
        assertEquals(mockBreed, (currentState as UiState.Success).data)
    }

    @Test
    fun `uiState wird zu Error, wenn die Katze nicht gefunden wird`() = runTest {
        every { getBreedByIdUseCase("cat123") } returns flowOf(null)

        viewModel = BreedDetailViewModel(
            savedStateHandle = savedStateHandle,
            getBreedByIdUseCase = getBreedByIdUseCase,
            updateVoteUseCase = updateVoteUseCase
        )
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState.collect()
        }

        advanceUntilIdle()
        val currentState = viewModel.uiState.value

        assertTrue("State sollte Error sein", currentState is UiState.Error)
        assertEquals(
            "Katze nicht in der Datenbank gefunden.",
            (currentState as UiState.Error).message
        )
    }

    @Test
    fun `toggleWeightUnit aendert isMetric von true auf false und wieder zurueck`() = runTest {
        every { getBreedByIdUseCase("cat123") } returns flowOf(mockk(relaxed = true))

        viewModel = BreedDetailViewModel(
            savedStateHandle = savedStateHandle,
            getBreedByIdUseCase = getBreedByIdUseCase,
            updateVoteUseCase = updateVoteUseCase
        )

        assertTrue("Initial sollte isMetric true sein", viewModel.isMetric.value)

        viewModel.toggleWeightUnit()
        assertFalse("Nach dem ersten Toggle sollte isMetric false sein", viewModel.isMetric.value)

        viewModel.toggleWeightUnit()
        assertTrue(
            "Nach dem zweiten Toggle sollte isMetric wieder true sein",
            viewModel.isMetric.value
        )
    }


    @Test
    fun `vote ruft updateVoteUseCase mit richtiger ID und Wert auf`() = runTest {
        val testBreedId = "cat123"
        val voteChange = 1

        every { getBreedByIdUseCase(testBreedId) } returns flowOf(mockk(relaxed = true))

        coEvery { updateVoteUseCase(testBreedId, voteChange) } just runs

        viewModel = BreedDetailViewModel(
            savedStateHandle = savedStateHandle,
            getBreedByIdUseCase = getBreedByIdUseCase,
            updateVoteUseCase = updateVoteUseCase
        )

        viewModel.vote(voteChange)

        advanceUntilIdle()

        coVerify(exactly = 1) { updateVoteUseCase(testBreedId, voteChange) }
    }
}