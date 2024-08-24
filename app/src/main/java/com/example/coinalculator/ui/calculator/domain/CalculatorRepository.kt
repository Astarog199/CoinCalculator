package com.example.coinalculator.ui.calculator.domain

import kotlinx.coroutines.flow.Flow

interface CalculatorRepository {
     fun consumeCoins() : Flow<List<CoinCalculator>>
}