package com.example.coinalculator.ui.calculator.data

import com.example.coinalculator.ui.calculator.domain.CalculatorModel

class CalculatorDomainMapper {

    fun fromEntity(calculatorEntity: CalculatorEntity) : CalculatorModel {
        return CalculatorModel(
            name = calculatorEntity.name,
            price = calculatorEntity.price
        )
    }

    fun toEntity(coinCalculator: CalculatorModel): CalculatorEntity {
        return CalculatorEntity(
            name = coinCalculator.name,
            price = coinCalculator.price
        )
    }

}