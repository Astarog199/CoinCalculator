package com.example.coinalculator.ui.calculator.data

import kotlinx.serialization.Serializable


@Serializable
data class CalculatorEntity (
    val name: String,
    val price: Long
)