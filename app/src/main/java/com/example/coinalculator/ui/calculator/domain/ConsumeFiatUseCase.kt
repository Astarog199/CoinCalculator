package com.example.coinalculator.ui.calculator.domain

import com.example.coinalculator.ui.calculator.data.CalculatorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ConsumeFiatUseCase @Inject constructor(
    private val repository: CalculatorRepository
) {
     operator fun invoke(): Flow<List<DomainEntity>> {
        return repository.consumeCalculatorFiat()
    }
}