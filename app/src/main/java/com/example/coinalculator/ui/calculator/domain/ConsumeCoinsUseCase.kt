package com.example.coinalculator.ui.calculator.domain

import kotlinx.coroutines.flow.Flow


class ConsumeCoinsUseCase(
    private val repository: CalculatorRepository
) {

     operator fun invoke(): Flow<List<CalculatorModel>> {
        return repository.consumeCoins()
    }
}