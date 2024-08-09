package com.example.coinalculator.ui.coins.domain

import com.example.coinalculator.ui.common.data.room.CoinEntity


class CoinsMapper {
    fun fromEntity(coin: CoinEntity) : CoinDetails {
        return CoinDetails(
            id = coin.id,
            name = coin.name,
            market = coin.market,
            price = coin.price,
            change24h = coin.price_percentage_change_24h,
            isFavorite = coin.isFavorite
        )
    }

    fun toEntity(coin: CoinDetails): CoinEntity {
        return CoinEntity(
            id = coin.id,
            name = coin.name,
            market = coin.market,
            price = coin.price,
            price_percentage_change_24h = coin.change24h,
            isFavorite = coin.isFavorite
        )
    }
}