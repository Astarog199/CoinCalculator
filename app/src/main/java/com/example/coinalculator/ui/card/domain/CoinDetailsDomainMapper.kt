package com.example.coinalculator.ui.card.domain

import com.example.coinalculator.ui.coins.domain.Coin

class CoinDetailsDomainMapper {

    fun fromEntity(coin: Coin) : CoinDetails {
        return CoinDetails(
            name = coin.name,
            market = coin.market,
            price = coin.price,
        )
    }
}