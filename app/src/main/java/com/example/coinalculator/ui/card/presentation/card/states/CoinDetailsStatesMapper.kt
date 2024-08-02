package com.example.coinalculator.ui.card.presentation.card.states

import com.example.coinalculator.ui.card.domain.CoinDetails

class CoinDetailsStatesMapper() {
    fun toCoinCardStates(coinDetails: CoinDetails): CoinCardStates{
        return CoinCardStates(
            name = coinDetails.name,
            market = coinDetails.market,
            price = coinDetails.price
        )
    }
}