package com.example.coinalculator.ui.calculator.data.models

import kotlinx.serialization.Serializable

@Serializable
data class CalculatorDataEntity (
    val symbol : String,
    val price: Float,
)