package com.example.coinalculator.ui.calculator.domain

import com.example.coinalculator.ui.calculator.data.CalculatorEntity
import kotlinx.coroutines.flow.Flow

interface CalculatorRepository {
    fun consumeCoins() : Flow<List<CoinCalculator>>
}