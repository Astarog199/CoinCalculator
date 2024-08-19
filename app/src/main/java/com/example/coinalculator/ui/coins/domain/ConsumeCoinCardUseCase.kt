package com.example.coinalculator.ui.coins.domain


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ConsumeCoinCardUseCase(
    private val coinsRepository: CoinsRepository,
) {

    operator fun invoke(itemId:String): Flow <Coin> {
        return coinsRepository.consumeCoins()
            .map { products ->
                products
                    .first { it.id.toString() == itemId }
            }
    }
}