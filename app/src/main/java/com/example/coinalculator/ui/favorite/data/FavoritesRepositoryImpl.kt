package com.example.coinalculator.ui.favorite.data

import com.example.coinalculator.ui.common.data.CommonRepositoryImpl
import com.example.coinalculator.ui.favorite.domain.FavoriteEntity
import com.example.coinalculator.ui.favorite.domain.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesRepositoryImpl(
    private val coinsRepository: CommonRepositoryImpl,
    private val favoriteMapper: FavoriteMapper
) : FavoriteRepository {

    override fun consumeFavoriteCoins(): Flow<List<FavoriteEntity>> {
        return coinsRepository.getList()
            .map { coins ->
                coins.filter { favorites ->
                    favorites.isFavorite
                }
                    .map(favoriteMapper::fromEntity)
            }
    }
}