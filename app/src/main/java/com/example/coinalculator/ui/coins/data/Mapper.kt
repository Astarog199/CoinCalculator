package com.example.coinalculator.ui.coins.data

import com.example.coinalculator.ui.coins.domain.Coin
import com.example.coinalculator.ui.coins.domain.CoinDetails
import com.example.coinalculator.ui.common.data.CoinsEntity
import com.example.coinalculator.ui.common.data.room.CoinEntity

class Mapper {

    fun fromEntity(coinEntity: CoinEntity) : Coin {
        return Coin(
            id = coinEntity.id,
            name = coinEntity.name,
            market = coinEntity.market,
            price = coinEntity.price,
        )
    }

    fun toEntity(coinDetails: CoinDetails) : CoinEntity {
        return CoinEntity(
            id = coinDetails.id,
            name = coinDetails.name,
            market = coinDetails.market,
            price = coinDetails.price,
            price_percentage_change_24h = coinDetails.change24h,
            isFavorite = coinDetails.isFavorite
        )
    }
}