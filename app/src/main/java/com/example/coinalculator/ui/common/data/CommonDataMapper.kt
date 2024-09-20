package com.example.coinalculator.ui.common.data

import com.example.coinalculator.ui.coins.domain.CoinEntity
import com.example.coinalculator.ui.common.data.room.Coin
import com.example.coinalculator.ui.favorite.domain.FavoriteEntity

class CommonDataMapper {
    fun toCoinEntity(coin: Coin) : CoinEntity {
        return CoinEntity(
            name = coin.name,
            image = coin.image,
            price = coin.price,
            pricePercentageChange24h = coin.price_percentage_change_24h,
            priceChange24h = coin.price_change_24h,
            marketCap = coin.market_cap,
            marketCapRank = coin.market_cap_rank,
            totalVolume = coin.total_volume,
            isFavorite = coin.isFavorite
        )
    }

    fun toCoin(coinEntity: CoinEntity) : Coin {
        return Coin(
            name = coinEntity.name,
            image = coinEntity.image,
            price = coinEntity.price,
            price_percentage_change_24h = coinEntity.pricePercentageChange24h,
            price_change_24h = coinEntity.priceChange24h,
            market_cap = coinEntity.marketCap,
            market_cap_rank = coinEntity.marketCapRank,
            total_volume = coinEntity.totalVolume,
            isFavorite = coinEntity.isFavorite
        )
    }

    fun toFavorite(entity: Coin) : FavoriteEntity {
        return FavoriteEntity(
            name = entity.name,
            image = entity.image,
            price = entity.price,
            change24h = entity.price_percentage_change_24h,
            isFavorite = entity.isFavorite
        )
    }
}

