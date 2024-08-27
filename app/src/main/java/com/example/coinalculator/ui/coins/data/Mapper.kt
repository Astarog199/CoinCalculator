package com.example.coinalculator.ui.coins.data

import com.example.coinalculator.ui.coins.domain.Coin
import com.example.coinalculator.ui.common.data.room.CoinEntity

class Mapper {

    fun fromEntity(coinEntity: CoinEntity) : Coin {
        return Coin(
            name = coinEntity.name,
            image = coinEntity.image,
            price = coinEntity.price,
            change24h = coinEntity.price_percentage_change_24h,
            isFavorite = coinEntity.isFavorite
        )
    }

    fun toEntity(coin: Coin) : CoinEntity {
        return CoinEntity(
            name = coin.name,
            image = coin.image,
            price = coin.price,
            price_percentage_change_24h = coin.change24h,
            isFavorite = coin.isFavorite
        )
    }
}