package com.example.coinalculator.ui.coins.domain


import com.example.coinalculator.ui.common.data.CommonRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ConsumeCoinCardUseCase(
    private val coinsRepository: CommonRepositoryImpl
) {

    operator fun invoke(name:String): Flow <CoinEntity> {
        return coinsRepository.consumeCoins()
            .map { products ->
                products
                    .first { it.name == name }
            }
    }
}