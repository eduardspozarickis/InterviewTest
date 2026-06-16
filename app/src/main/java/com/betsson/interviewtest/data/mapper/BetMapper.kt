package com.betsson.interviewtest.data.mapper

import com.betsson.interviewtest.data.entity.BetDTO
import com.betsson.interviewtest.domain.entity.Bet

object BetMapper {

    fun BetDTO.toDomain(): Bet {
        return Bet(
            type = this.type,
            sellIn = this.sellIn,
            odds = this.odds,
            image = this.image
        )
    }
}