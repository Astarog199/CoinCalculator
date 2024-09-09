package com.example.coinalculator.ui.coins.domain


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ConsumeCoinCardUseCase(
    private val coinsRepository: CoinsRepository,
) {

    operator fun invoke(name:String): Flow <CoinEntity> {
        return coinsRepository.consumeCoins()
            .map { products ->
                products
                    .first { it.name == name }
            }
    }
}