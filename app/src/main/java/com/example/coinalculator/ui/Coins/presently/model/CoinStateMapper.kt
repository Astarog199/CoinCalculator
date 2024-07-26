package com.example.coinalculator.ui.Coins.presently.model

import com.example.coinalculator.ui.Coins.domain.Coin

class CoinStateMapper () {
    fun toCoinState(coin: Coin) : CoinState{
        return CoinState(
            name = coin.name,
            market = coin.market,
            price = coin.price,
        )
    }
}