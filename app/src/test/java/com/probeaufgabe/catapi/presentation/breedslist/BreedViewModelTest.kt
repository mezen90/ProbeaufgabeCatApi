package com.probeaufgabe.catapi.presentation.breedslist

import com.probeaufgabe.catapi.domain.usecase.GetPagedBreedsUseCase
import com.probeaufgabe.catapi.domain.usecase.UpdateVoteUseCase
import com.probeaufgabe.catapi.presentation.breeds.BreedViewModel
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BreedViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `toggleSort ändert den sortByWeight State von false auf true`() {
        val mockGetPagedBreedsUseCase = mockk<GetPagedBreedsUseCase>(relaxed = true)
        val mockUpdateVoteUseCase = mockk<UpdateVoteUseCase>(relaxed = true)

        val viewModel = BreedViewModel(
            getPagedBreedsUseCase = mockGetPagedBreedsUseCase,
            updateVoteUseCase = mockUpdateVoteUseCase
        )

        assertFalse(viewModel.sortByWeight.value)

        viewModel.toggleSort()

        assertTrue(viewModel.sortByWeight.value)
    }
}
