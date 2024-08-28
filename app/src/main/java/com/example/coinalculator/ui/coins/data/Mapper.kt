package com.example.coinalculator.ui.coins.data

import com.example.coinalculator.ui.coins.domain.Coin
import com.example.coinalculator.ui.common.data.room.CoinEntity

class Mapper {

    fun fromEntity(coinEntity: CoinEntity) : Coin {
        return Coin(
            name = coinEntity.name,
            image = coinEntity.image,
            price = coinEntity.price,
            pricePercentageChange24h = coinEntity.price_percentage_change_24h,
            priceChange24h = coinEntity.price_change_24h,
            marketCap = coinEntity.market_cap,
            marketCapRank = coinEntity.market_cap_rank,
            totalVolume = coinEntity.total_volume,
            isFavorite = coinEntity.isFavorite
        )
    }

    fun toEntity(coin: Coin) : CoinEntity {
        return CoinEntity(
            name = coin.name,
            image = coin.image,
            price = coin.price,
            price_percentage_change_24h = coin.pricePercentageChange24h,
            price_change_24h = coin.priceChange24h,
            market_cap = coin.marketCap,
            market_cap_rank = coin.marketCapRank,
            total_volume = coin.totalVolume,
            isFavorite = coin.isFavorite
        )
    }
}