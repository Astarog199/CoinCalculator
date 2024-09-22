package com.example.coinalculator.ui.favorite.domain

import com.example.coinalculator.ui.common.data.CommonRepositoryImpl
import kotlinx.coroutines.flow.Flow

class ConsumeFavoritesUseCase(
    private val coinsRepository: CommonRepositoryImpl
) {

    operator fun invoke(): Flow<List<FavoriteEntity>>{
        return coinsRepository.consumeFavoriteCoins()
    }
}