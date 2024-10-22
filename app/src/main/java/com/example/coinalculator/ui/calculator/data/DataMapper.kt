package com.example.coinalculator.ui.calculator.data

import com.example.coinalculator.ui.calculator.data.models.CalculatorDataEntity
import com.example.coinalculator.ui.calculator.data.models.dto.RubDTO
import com.example.coinalculator.ui.calculator.domain.DomainEntity
import javax.inject.Inject

class DataMapper @Inject constructor(){
    fun rubToDataEntity(coin: RubDTO, name: String): CalculatorDataEntity {
        return CalculatorDataEntity(
            symbol = name,
            price = coin.price.usd
        )
    }

    fun toDomainEntity(entity: CalculatorDataEntity) : DomainEntity {
        return DomainEntity(
            symbol = entity.symbol,
            price = entity.price
        )
    }
}