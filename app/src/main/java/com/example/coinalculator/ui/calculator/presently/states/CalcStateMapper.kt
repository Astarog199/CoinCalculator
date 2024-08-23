package com.example.coinalculator.ui.calculator.presently.states

import com.example.coinalculator.ui.calculator.domain.CoinCalculator

class CalcStateMapper () {
    fun toCoinCalState(coinCalculator: CoinCalculator) : CoinCalState {
        return CoinCalState(
            name = coinCalculator.name,
            price = coinCalculator.price
        )
    }
}