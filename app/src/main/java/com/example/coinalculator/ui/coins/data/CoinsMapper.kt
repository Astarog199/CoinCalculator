package com.example.coinalculator.ui.coins.data

import com.example.coinalculator.ui.coins.domain.Coin
import com.example.coinalculator.ui.common.data.CoinsEntity

class CoinsMapper {

    fun fromEntity(coinsEntity: CoinsEntity) : Coin {
        return Coin(
            id = coinsEntity.id,
            name = coinsEntity.name,
            market = coinsEntity.market,
            price = coinsEntity.price,
            change24h = coinsEntity.price_percentage_change_24h
        )
    }

    fun toEntity(coin: Coin): CoinsEntity {
        return CoinsEntity(
            id = coin.id,
            name = coin.name,
            market = coin.market,
            price = coin.price,
            price_percentage_change_24h = coin.change24h
        )
    }
}