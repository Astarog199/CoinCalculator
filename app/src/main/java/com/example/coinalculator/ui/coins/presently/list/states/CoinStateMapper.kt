package com.example.coinalculator.ui.coins.presently.list.states

import com.example.coinalculator.ui.coins.domain.CoinEntity

class CoinStateMapper () {
    fun toCoinState(coin: CoinEntity) : CoinState {
        return CoinState(
            name = coin.name,
            image = coin.image,
            price = coin.price,
            isFavorite = coin.isFavorite
        )
    }
}