package com.example.coinalculator.ui.Coins.domain

import kotlinx.coroutines.flow.Flow

interface CoinsRepository {
    fun consumeCoins() : Flow<List<Coin>>
}