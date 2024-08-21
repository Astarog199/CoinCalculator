package com.example.coinalculator.ui.calculator.data

class CalculatorDataMapper {
    fun toEntity(coin : ModelDto) : CalculatorEntity {
        return CalculatorEntity(
            price = coin.coinUsd.usd
        )
    }
}