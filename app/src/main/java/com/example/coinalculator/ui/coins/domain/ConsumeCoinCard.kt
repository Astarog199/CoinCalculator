package com.example.coinalculator.ui.coins.domain


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ConsumeCoinCard(
    private val coinsRepository: CoinsRepository,
    private val cardMapper: CardMapper
) {

    operator fun invoke(itemId:String): Flow <CoinDetails> {
        return coinsRepository.consumeCoins()
            .map { products ->
                products
                    .first { it.id.toString() == itemId }
                    .run(cardMapper::fromEntity)
            }
    }
}