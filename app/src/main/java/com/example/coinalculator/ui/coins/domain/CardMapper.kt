package com.example.coinalculator.ui.coins.domain

import com.example.coinalculator.ui.common.data.CoinsEntity

class CardMapper {
    fun fromEntity(coin: Coin) : CoinDetails {
        return CoinDetails(
            id = coin.id,
            name = coin.name,
            market = coin.market,
            price = coin.price,
            change24h = coin.change24h
        )
    }
}