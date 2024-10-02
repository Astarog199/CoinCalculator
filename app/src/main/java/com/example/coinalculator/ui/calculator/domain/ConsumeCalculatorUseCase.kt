package com.example.coinalculator.ui.calculator.domain

import com.example.coinalculator.ui.common.data.CommonRepositoryImpl
import kotlinx.coroutines.flow.Flow


class ConsumeCalculatorUseCase(
    private val commonRepositoryImpl: CommonRepositoryImpl
) {
     operator fun invoke(): Flow<List<DomainEntity>> {
        return commonRepositoryImpl.consumeCalculatorCoins()
    }
}