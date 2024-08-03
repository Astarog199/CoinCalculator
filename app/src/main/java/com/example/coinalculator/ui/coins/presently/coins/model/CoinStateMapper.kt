package com.example.coinalculator.ui.coins.presently.coins.model

import com.example.coinalculator.ui.coins.domain.Coin

class CoinStateMapper () {
    fun toCoinState(coin: Coin) : CoinState {
        return CoinState(
            id = coin.id,
            name = coin.name,
            market = coin.market,
            price = coin.price,
        )
    }
}