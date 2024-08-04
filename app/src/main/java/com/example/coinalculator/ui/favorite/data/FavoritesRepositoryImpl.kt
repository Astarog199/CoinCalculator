package com.example.coinalculator.ui.favorite.data

import com.example.coinalculator.ui.common.data.CommonRepository
import com.example.coinalculator.ui.favorite.domain.Favorite
import com.example.coinalculator.ui.favorite.domain.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesRepositoryImpl(
    private val coinsRepository: CommonRepository,
    private val favoriteMapper: FavoriteMapper
) : FavoriteRepository {

    override fun consumeFavoriteCoins(): Flow<List<Favorite>> {
        return coinsRepository.saveList()
            .map { coins ->
                coins.filter { favorites ->
                    favorites.isFavorite
                }
                    .map(favoriteMapper::fromEntity)
            }
    }

    override fun addFavorite(id: String) {
        TODO("Not yet implemented")
    }

    override fun removeFavorite(id: String) {
        TODO("Not yet implemented")
    }
}