package com.example.coinalculator.ui.coins.domain

import kotlinx.coroutines.flow.Flow

interface CoinsRepository {
    fun consumeCoins() : Flow<List<Coin>>
}