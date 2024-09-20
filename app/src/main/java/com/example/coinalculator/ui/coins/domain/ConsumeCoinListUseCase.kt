package com.example.coinalculator.ui.coins.domain

import com.example.coinalculator.ui.common.data.CommonRepositoryImpl
import kotlinx.coroutines.flow.Flow

class ConsumeCoinListUseCase(
    private val coinsRepository: CommonRepositoryImpl
) {
    operator fun invoke(): Flow<List<CoinEntity>> {
        return coinsRepository.consumeCoins()
    }
}