package com.betsson.interviewtest.domain.repository

import com.betsson.interviewtest.data.mapper.BetMapper.toDomain
import com.betsson.interviewtest.data.remote.RemoteStore
import com.betsson.interviewtest.domain.entity.Bet

class BetsRepository(
    private val remoteStore: RemoteStore
) {

    fun getBets(): List<Bet> {
        return remoteStore.getBets().map { it.toDomain() }
    }
}