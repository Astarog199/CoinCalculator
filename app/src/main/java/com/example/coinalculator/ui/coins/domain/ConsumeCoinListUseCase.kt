package com.example.coinalculator.ui.coins.domain

import com.example.coinalculator.ui.common.data.CommonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConsumeCoinListUseCase @Inject constructor(
    private val repository: CommonRepository
) {
    operator fun invoke(): Flow<List<CoinEntity>> {
        return repository.consumeCoins()
    }
}