package com.betsson.interviewtest.domain.usecase

import com.betsson.interviewtest.domain.entity.Bet
import com.betsson.interviewtest.domain.repository.BetsRepository

class FetchBetsListUseCase(
    private val repository: BetsRepository
) {

    fun launch(): List<Bet> {
        return repository.getBets()
    }
}