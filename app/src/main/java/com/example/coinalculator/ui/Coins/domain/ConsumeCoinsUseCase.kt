package com.example.coinalculator.ui.Coins.domain

import kotlinx.coroutines.flow.Flow

class ConsumeCoinsUseCase(
    private val coinsRepository: CoinsRepository
) {

    operator fun invoke(): Flow<List<Coin>> {
        return coinsRepository.consumeCoins()
    }
}