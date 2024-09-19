package com.example.coinalculator.ui.coins.domain

import kotlinx.coroutines.flow.Flow

interface CoinsRepository {
    fun consumeCoins() : Flow<List<CoinEntity>>
    fun filterCoins(query: String) : Flow<List<CoinEntity>>
    suspend fun changeFavoriteState(coin: CoinEntity)
}