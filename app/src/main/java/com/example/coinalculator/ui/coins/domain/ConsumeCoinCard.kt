package com.example.coinalculator.ui.coins.domain


import com.example.coinalculator.ui.common.data.CommonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ConsumeCoinCard(
    private val coinsRepository: CommonRepository,
    private val cardMapper: CoinsMapper
) {

    operator fun invoke(itemId:String): Flow <CoinDetails> {
        return coinsRepository.saveList()
            .map { products ->
                products
                    .first { it.id.toString() == itemId }
                    .run(cardMapper::fromEntity)
            }
    }
}