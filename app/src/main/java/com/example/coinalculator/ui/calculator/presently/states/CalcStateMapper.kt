package com.example.coinalculator.ui.calculator.presently.states

import com.example.coinalculator.ui.calculator.domain.DomainEntity
import javax.inject.Inject

class CalcStateMapper @Inject constructor() {
    fun toCoinCalState(coinCalculator: DomainEntity) : CoinCalState {
        return CoinCalState(
            symbol = coinCalculator.symbol,
            price = coinCalculator.price
        )
    }
}