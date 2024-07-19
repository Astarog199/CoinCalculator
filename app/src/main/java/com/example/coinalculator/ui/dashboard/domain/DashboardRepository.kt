package com.example.coinalculator.ui.dashboard.domain

import kotlinx.coroutines.flow.Flow

interface DashboardRepository {
    suspend fun consumeCoins() : Flow<List<Coin>>
}