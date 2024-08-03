package com.example.coinalculator.ui.coins.presently.card.states

import com.example.coinalculator.ui.coins.domain.CoinDetails

class CoinDetailsStatesMapper() {
    fun toCoinCardStates(coinDetails: CoinDetails): CoinCardStates {
        return CoinCardStates(
            name = coinDetails.name,
            market = coinDetails.market,
            price = coinDetails.price,
            change24h = coinDetails.change24h
        )
    }
}