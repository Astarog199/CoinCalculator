package com.example.coinalculator.ui.Coins.domain

import kotlinx.coroutines.flow.Flow

class ConsumeCoinsUseCase(
    private val coinsRepository: CoinsRepository
) {
    var coins: List<Coin> = mutableListOf()

    operator fun invoke(): Flow<List<Coin>> {
        return coinsRepository.consumeCoins()
    }
}