package com.example.coinalculator.ui.Coins.data

import com.example.coinalculator.ui.Coins.domain.Coin

class CoinsMapper {

    fun fromEntity(coinsEntity: CoinsEntity) : Coin {
        return Coin(
            name = coinsEntity.name,
            market = coinsEntity.market,
            price = coinsEntity.price,
        )
    }

    fun toEntity(coin: Coin): CoinsEntity {
        return CoinsEntity(
            id = 0,
            name = coin.name,
            market = coin.market,
            price = coin.price,
        )
    }
}