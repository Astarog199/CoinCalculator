package com.example.coinalculator.ui.calculator.data

import com.example.coinalculator.ui.calculator.domain.CoinCalculator

class CalculatorDomainMapper {

    fun fromEntity(calculatorEntity: CalculatorEntity) : CoinCalculator {
        return CoinCalculator(
            price = calculatorEntity.price
        )
    }

    fun toEntity(coinCalculator: CoinCalculator): CalculatorEntity {
        return CalculatorEntity(
            price = coinCalculator.price
        )
    }

}