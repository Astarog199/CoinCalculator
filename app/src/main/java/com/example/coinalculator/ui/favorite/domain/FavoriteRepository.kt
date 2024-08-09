package com.example.coinalculator.ui.favorite.domain

import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun consumeFavoriteCoins() : Flow<List<Favorite>>
}