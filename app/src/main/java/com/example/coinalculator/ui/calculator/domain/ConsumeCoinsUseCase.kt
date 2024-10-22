package com.example.coinalculator.ui.calculator.domain

import com.example.coinalculator.ui.common.data.CommonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConsumeCoinsUseCase @Inject constructor(
    private val repository: CommonRepository
) {
    operator fun invoke(): Flow<List<DomainEntity>> {
        return repository.consumeCalculatorCoins()
    }
}