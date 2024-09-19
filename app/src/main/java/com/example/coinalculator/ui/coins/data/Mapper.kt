package com.example.coinalculator.ui.coins.data

import com.example.coinalculator.ui.coins.domain.CoinEntity
import com.example.coinalculator.ui.common.data.room.Coin

class Mapper {

    fun fromEntity(entity: Coin) : CoinEntity {
        return CoinEntity(
            name = entity.name,
            image = entity.image,
            price = entity.price,
            pricePercentageChange24h = entity.price_percentage_change_24h,
            priceChange24h = entity.price_change_24h,
            marketCap = entity.market_cap,
            marketCapRank = entity.market_cap_rank,
            totalVolume = entity.total_volume,
            isFavorite = entity.isFavorite
        )
    }

    fun toEntity(coin: CoinEntity) : Coin {
        return Coin(
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