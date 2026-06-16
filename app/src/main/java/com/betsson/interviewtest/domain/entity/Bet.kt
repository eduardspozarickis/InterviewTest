package com.betsson.interviewtest.domain.entity

data class Bet(
    val type: String,
    val sellIn: Int,
    val odds: Int,
    val image: String
) {

    override fun toString(): String {
        return this.type + ", " + this.sellIn + ", " + this.odds
    }
}