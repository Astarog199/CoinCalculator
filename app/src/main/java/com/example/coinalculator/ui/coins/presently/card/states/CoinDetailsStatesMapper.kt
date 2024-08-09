package com.example.coinalculator.ui.coins.presently.card.states

import com.example.coinalculator.ui.coins.domain.Coin
import com.example.coinalculator.ui.coins.domain.CoinDetails

class CoinDetailsStatesMapper() {
    fun toCoinCardStates(coinDetails: CoinDetails): CoinCardStates {
        return CoinCardStates(
            id = coinDetails.id,
            name = coinDetails.name,
            market = coinDetails.market,
            price = coinDetails.price,
            change24h = coinDetails.change24h,
            isFavorite = coinDetails.isFavorite
        )
    }

    fun toCoinDetails(coin: CoinCardStates) : CoinDetails {
        return CoinDetails(
            id = coin.id,
            name = coin.name,
            market = coin.market,
            price = coin.price,
            change24h = coin.change24h,
            isFavorite = coin.isFavorite
        )
    }
}