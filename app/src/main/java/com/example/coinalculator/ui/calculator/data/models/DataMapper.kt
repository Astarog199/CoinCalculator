package com.example.coinalculator.ui.calculator.data.models

import com.example.coinalculator.ui.calculator.data.CalculatorEntity
import com.example.coinalculator.ui.common.data.CoinsDto

class DataMapper {
    fun rubToEntity(coin: RubDTO, name: String) : CalculatorEntity {
        return CalculatorEntity(
            name = name,
            price = coin.price.usd
        )
    }

    fun toEntity(coinEntity: CoinsDto) : CalculatorEntity {
        return CalculatorEntity(
            name = coinEntity.symbol,
            price = coinEntity.currentPrice
        )
    }
}