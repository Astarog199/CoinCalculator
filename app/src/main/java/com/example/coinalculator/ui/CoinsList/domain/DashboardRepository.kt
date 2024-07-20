package com.example.coinalculator.ui.CoinsList.domain

import kotlinx.coroutines.flow.Flow

interface DashboardRepository {
    suspend fun consumeCoins() : Flow<List<Coin>>
}