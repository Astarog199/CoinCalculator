package com.example.coinalculator.ui.coins.domain


import com.example.coinalculator.ui.common.data.CommonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ConsumeCoinCardUseCase @Inject constructor(
    private val repository: CommonRepository
) {

    operator fun invoke(name:String): Flow <CoinEntity> {
        return repository.consumeCoins()
            .map { products ->
                products
                    .first { it.name == name }
            }
    }
}