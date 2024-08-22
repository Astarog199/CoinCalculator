package com.example.coinalculator.ui.calculator.data

import com.example.coinalculator.ui.calculator.data.models.DTO

class CalculatorDataMapper {
    fun toEntity(coin: DTO, name: String) : CalculatorEntity {
        return CalculatorEntity(
            name = name,
            price = coin.bitcoinUsd.usd
        )
    }
}