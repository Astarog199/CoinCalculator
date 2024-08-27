package com.example.coinalculator.ui.coins.presently.list.states

import com.example.coinalculator.ui.coins.domain.Coin

class CoinStateMapper () {
    fun toCoinState(coin: Coin) : CoinState {
        return CoinState(
            name = coin.name,
            image = coin.image,
            price = coin.price,
        )
    }
}