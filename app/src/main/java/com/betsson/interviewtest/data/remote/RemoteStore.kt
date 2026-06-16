package com.betsson.interviewtest.data.remote

import com.betsson.interviewtest.data.entity.BetDTO

class RemoteStore(
    // val api: Api // TODO: potential
) {

    /**
     * Return of mocked data until API implemented
     */
    fun getBets(): List<BetDTO> {
        return listOf(
            BetDTO("Winning team", 10, 20, "https://i.imgur.com/mx66SBD.jpeg"),
            BetDTO("Total score", 2, 0, "https://i.imgur.com/VnPRqcv.jpeg"),
            BetDTO("Player performance", 5, 7, "https://i.imgur.com/Urpc00H.jpeg"),
            BetDTO("First goal scorer", 0, 80, "https://i.imgur.com/Wy94Tt7.jpeg"),
            BetDTO("Number of fouls", 5, 49, "https://i.imgur.com/NMLpcKj.jpeg"),
            BetDTO("Corner kicks", 3, 6, "https://i.imgur.com/TiJ8y5l.jpeg"),
        )
    }
}