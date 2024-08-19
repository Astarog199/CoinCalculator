package com.example.coinalculator.ui.coins.domain

import kotlinx.coroutines.flow.Flow

class ConsumeCoinListUseCase(
    private val coinsRepository: CoinsRepository
) {
    operator fun invoke(): Flow<List<Coin>> {
        return coinsRepository.consumeCoins()
    }
}