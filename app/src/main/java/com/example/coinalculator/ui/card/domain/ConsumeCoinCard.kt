package com.example.coinalculator.ui.card.domain

import com.example.coinalculator.ui.coins.domain.CoinsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ConsumeCoinCard(
    private val coinsRepository: CoinsRepository,
    private val coinDetailsDomainMapper: CoinDetailsDomainMapper
) {

    operator fun invoke(itemId:String): Flow <CoinDetails> {
        return coinsRepository.consumeCoins()
            .map { products ->
                products
                    .first { it.id.toString() == itemId }
                    .run(coinDetailsDomainMapper::fromEntity)
            }
    }
}