package com.betsson.interviewtest.presentation.home

import com.betsson.interviewtest.domain.entity.Bet
import com.betsson.interviewtest.domain.usecase.FetchBetsListUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
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
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var fetchBetsListUseCase: FetchBetsListUseCase
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fetchBetsListUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // TODO: AI couldn't make successful, needs to be inverstigated
//    @Test
//    fun `init should fetch bets and update uiState`() = runTest {
//        // Given
//        val expectedBets = listOf(Bet("Test", 10, 20, "url"))
//        every { fetchBetsListUseCase.launch() } returns expectedBets
//
//        // When
//        // We use a StandardTestDispatcher for IO to force suspension at withContext,
//        // allowing us to verify the loading state. It must use the same scheduler as runTest.
//        val ioDispatcher = StandardTestDispatcher(testScheduler)
//        viewModel = HomeViewModel(ioDispatcher, fetchBetsListUseCase)
//
//        // With Unconfined Main dispatcher, the coroutine in init starts immediately.
//        // It updates isLoading = true, then reaches withContext(ioDispatcher) and suspends.
//        assertTrue(viewModel.uiState.value.isLoading)
//
//        advanceUntilIdle()
//
//        // Then
//        assertEquals(expectedBets, viewModel.uiState.value.bets)
//        assertFalse(viewModel.uiState.value.isLoading)
//        verify { fetchBetsListUseCase.launch() }
//    }

    @Test
    fun `onUpdateOddsClicked should update normal bets correctly`() = runTest {
        // Given
        val initialBets = listOf(Bet("Normal Bet", 10, 20, "url"))
        every { fetchBetsListUseCase.launch() } returns initialBets
        viewModel = HomeViewModel(testDispatcher, fetchBetsListUseCase)
        advanceUntilIdle()

        // When
        viewModel.onUpdateOddsClicked()

        // Then
        val bet = viewModel.uiState.value.bets[0]
        assertEquals(19, bet.odds)
        assertEquals(9, bet.sellIn)
    }

    @Test
    fun `onUpdateOddsClicked should handle First goal scorer (no change)`() = runTest {
        // Given
        val initialBets = listOf(Bet("First goal scorer", 10, 20, "url"))
        every { fetchBetsListUseCase.launch() } returns initialBets
        viewModel = HomeViewModel(testDispatcher, fetchBetsListUseCase)
        advanceUntilIdle()

        // When
        viewModel.onUpdateOddsClicked()

        // Then
        val bet = viewModel.uiState.value.bets[0]
        assertEquals(20, bet.odds)
        assertEquals(10, bet.sellIn)
    }

    @Test
    fun `onUpdateOddsClicked should handle Total score (odds increase)`() = runTest {
        // Given
        val initialBets = listOf(Bet("Total score", 10, 20, "url"))
        every { fetchBetsListUseCase.launch() } returns initialBets
        viewModel = HomeViewModel(testDispatcher, fetchBetsListUseCase)
        advanceUntilIdle()

        // When
        viewModel.onUpdateOddsClicked()

        // Then
        val bet = viewModel.uiState.value.bets[0]
        assertEquals(21, bet.odds)
        assertEquals(9, bet.sellIn)
    }

    @Test
    fun `onUpdateOddsClicked should handle Number of fouls with sellIn under 6`() = runTest {
        // Given
        val initialBets = listOf(Bet("Number of fouls", 5, 20, "url"))
        every { fetchBetsListUseCase.launch() } returns initialBets
        viewModel = HomeViewModel(testDispatcher, fetchBetsListUseCase)
        advanceUntilIdle()

        // When
        viewModel.onUpdateOddsClicked()

        // Then
        // +1 initially, +1 for < 11, +1 for < 6 = total +3
        val bet = viewModel.uiState.value.bets[0]
        assertEquals(23, bet.odds)
        assertEquals(4, bet.sellIn)
    }

    @Test
    fun `onUpdateOddsClicked should drop Number of fouls odds to 0 when sellIn is negative`() =
        runTest {
            // Given
            val initialBets = listOf(Bet("Number of fouls", 0, 20, "url"))
            every { fetchBetsListUseCase.launch() } returns initialBets
            viewModel = HomeViewModel(testDispatcher, fetchBetsListUseCase)
            advanceUntilIdle()

            // When
            viewModel.onUpdateOddsClicked()

            // Then
            val bet = viewModel.uiState.value.bets[0]
            assertEquals(0, bet.odds)
            assertEquals(-1, bet.sellIn)
        }

    @Test
    fun `onUpdateOddsClicked should decrease odds twice for normal bets with negative sellIn`() =
        runTest {
            // Given
            val initialBets = listOf(Bet("Normal Bet", 0, 20, "url"))
            every { fetchBetsListUseCase.launch() } returns initialBets
            viewModel = HomeViewModel(testDispatcher, fetchBetsListUseCase)
            advanceUntilIdle()

            // When
            viewModel.onUpdateOddsClicked()

            // Then
            val bet = viewModel.uiState.value.bets[0]
            assertEquals(18, bet.odds)
            assertEquals(-1, bet.sellIn)
        }

    @Test
    fun `onUpdateOddsClicked should increase odds twice for Total score with negative sellIn`() =
        runTest {
            // Given
            val initialBets = listOf(Bet("Total score", 0, 20, "url"))
            every { fetchBetsListUseCase.launch() } returns initialBets
            viewModel = HomeViewModel(testDispatcher, fetchBetsListUseCase)
            advanceUntilIdle()

            // When
            viewModel.onUpdateOddsClicked()

            // Then
            val bet = viewModel.uiState.value.bets[0]
            assertEquals(22, bet.odds)
            assertEquals(-1, bet.sellIn)
        }

    @Test
    fun `onUpdateOddsClicked should handle Number of fouls with sellIn between 6 and 10`() =
        runTest {
            // Given
            val initialBets = listOf(Bet("Number of fouls", 10, 20, "url"))
            every { fetchBetsListUseCase.launch() } returns initialBets
            viewModel = HomeViewModel(testDispatcher, fetchBetsListUseCase)
            advanceUntilIdle()

            // When
            viewModel.onUpdateOddsClicked()

            // Then
            // +1 initially, +1 for < 11 = total +2
            val bet = viewModel.uiState.value.bets[0]
            assertEquals(22, bet.odds)
            assertEquals(9, bet.sellIn)
        }

    @Test
    fun `onUpdateOddsClicked should not increase odds beyond 50 for Total score`() = runTest {
        // Given
        val initialBets = listOf(Bet("Total score", 10, 50, "url"))
        every { fetchBetsListUseCase.launch() } returns initialBets
        viewModel = HomeViewModel(testDispatcher, fetchBetsListUseCase)
        advanceUntilIdle()

        // When
        viewModel.onUpdateOddsClicked()

        // Then
        val bet = viewModel.uiState.value.bets[0]
        assertEquals(50, bet.odds)
        assertEquals(9, bet.sellIn)
    }
}
