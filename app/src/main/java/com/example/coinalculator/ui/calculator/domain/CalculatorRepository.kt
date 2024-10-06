package com.example.coinalculator.ui.calculator.domain

import kotlinx.coroutines.flow.Flow

interface CalculatorRepository {
     fun consumeCalculatorCoins() : Flow<List<DomainEntity>>
}