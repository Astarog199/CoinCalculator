package com.example.coinalculator.ui.favorite.data

import com.example.coinalculator.ui.common.data.CommonEntity
import com.example.coinalculator.ui.common.data.room.CoinEntity
import com.example.coinalculator.ui.favorite.domain.Favorite

class FavoriteMapper {
    fun fromEntity(coinsEntity: CoinEntity) : Favorite {
        return Favorite(
            name = coinsEntity.name,
            image = coinsEntity.image,
            price = coinsEntity.price,
            change24h = coinsEntity.price_percentage_change_24h,
            isFavorite = coinsEntity.isFavorite
        )
    }

    fun toEntity(coin: Favorite): CommonEntity {
        return CommonEntity(
            name = coin.name,
            image = coin.image,
            price = coin.price,
            price_percentage_change_24h = coin.change24h,
            isFavorite = coin.isFavorite
        )
    }
}