package com.betsson.interviewtest.domain.usecase

import com.betsson.interviewtest.domain.entity.Bet
import com.betsson.interviewtest.domain.repository.BetsRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FetchBetsListUseCaseTest {

    private lateinit var repository: BetsRepository
    private lateinit var useCase: FetchBetsListUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = FetchBetsListUseCase(repository)
    }

    @Test
    fun `launch should return list of bets from repository`() {
        // Given
        val expectedBets = listOf(
            Bet("Winning team", 10, 20, "url1"),
            Bet("Total score", 2, 0, "url2")
        )
        every { repository.getBets() } returns expectedBets

        // When
        val result = useCase.launch()

        // Then
        assertEquals(expectedBets, result)
        verify { repository.getBets() }
    }
}
