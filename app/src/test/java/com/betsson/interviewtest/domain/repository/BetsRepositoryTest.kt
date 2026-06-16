package com.betsson.interviewtest.domain.repository

import com.betsson.interviewtest.data.entity.BetDTO
import com.betsson.interviewtest.data.remote.RemoteStore
import com.betsson.interviewtest.domain.entity.Bet
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BetsRepositoryTest {

    private lateinit var remoteStore: RemoteStore
    private lateinit var repository: BetsRepository

    @Before
    fun setUp() {
        remoteStore = mockk()
        repository = BetsRepository(remoteStore)
    }

    @Test
    fun `getBets should return list of bets from remote store`() {
        // Given
        val mockDtoList = listOf(
            BetDTO("Winning team", 10, 20, "url1"),
            BetDTO("Total score", 2, 0, "url2")
        )
        val expectedBets = listOf(
            Bet("Winning team", 10, 20, "url1"),
            Bet("Total score", 2, 0, "url2")
        )
        every { remoteStore.getBets() } returns mockDtoList

        // When
        val result = repository.getBets()

        // Then
        assertEquals(expectedBets, result)
        verify { remoteStore.getBets() }
    }

    @Test
    fun `getBets should return empty list when remote store returns empty list`() {
        // Given
        every { remoteStore.getBets() } returns emptyList()

        // When
        val result = repository.getBets()

        // Then
        assertEquals(0, result.size)
        verify { remoteStore.getBets() }
    }
}
