package com.example.coinalculator.ui.coins.presently.card.states

import com.example.coinalculator.ui.coins.domain.Coin

class CoinDetailsStatesMapper() {
    fun toCoinCardStates(coin: Coin): CoinCardStates {
        return CoinCardStates(
            id = coin.id,
            name = coin.name,
            image = coin.image,
            price = coin.price,
            change24h = coin.change24h,
            isFavorite = coin.isFavorite
        )
    }

    fun toCoinDetails(coin: CoinCardStates) : Coin {
        return Coin(
            id = coin.id,
            name = coin.name,
            image = coin.image,
            price = coin.price,
            change24h = coin.change24h,
            isFavorite = coin.isFavorite
        )
    }
}