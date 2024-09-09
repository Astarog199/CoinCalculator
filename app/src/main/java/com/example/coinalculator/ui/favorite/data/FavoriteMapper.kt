package com.example.coinalculator.ui.favorite.data

import com.example.coinalculator.ui.common.data.room.Entity
import com.example.coinalculator.ui.favorite.domain.FavoriteEntity

class FavoriteMapper {
    fun fromEntity(entity: Entity) : FavoriteEntity {
        return FavoriteEntity(
            name = entity.name,
            image = entity.image,
            price = entity.price,
            change24h = entity.price_percentage_change_24h,
            isFavorite = entity.isFavorite
        )
    }
}