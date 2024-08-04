package com.example.coinalculator.ui.favorite.data

import com.example.coinalculator.ui.common.data.CoinsEntity
import com.example.coinalculator.ui.favorite.domain.Favorite

class FavoriteMapper {
    fun fromEntity(coinsEntity: CoinsEntity) : Favorite {
        return Favorite(
            id = coinsEntity.id,
            name = coinsEntity.name,
            market = coinsEntity.market,
            price = coinsEntity.price,
            change24h = coinsEntity.price_percentage_change_24h,
            isFavorite = coinsEntity.isFavorite
        )
    }

    fun toEntity(coin: Favorite): CoinsEntity {
        return CoinsEntity(
            id = coin.id,
            name = coin.name,
            market = coin.market,
            price = coin.price,
            price_percentage_change_24h = coin.change24h,
            isFavorite = coin.isFavorite
        )
    }
}