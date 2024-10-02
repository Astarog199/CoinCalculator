package com.example.coinalculator.ui.calculator.presently.states

import com.example.coinalculator.ui.calculator.domain.DomainEntity

class CalcStateMapper () {
    fun toCoinCalState(coinCalculator: DomainEntity) : CoinCalState {
        return CoinCalState(
            name = coinCalculator.name,
            price = coinCalculator.price
        )
    }
}