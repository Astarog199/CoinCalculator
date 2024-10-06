package com.example.coinalculator.ui.calculator.domain

import kotlinx.coroutines.flow.Flow


class ConsumeCalculatorUseCase(
    private val commonRepositoryImpl: CalculatorRepository
) {
     operator fun invoke(): Flow<List<DomainEntity>> {
        return commonRepositoryImpl.consumeCalculatorCoins()
    }
}