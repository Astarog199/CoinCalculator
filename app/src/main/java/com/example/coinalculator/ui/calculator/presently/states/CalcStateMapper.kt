package com.example.coinalculator.ui.calculator.presently.states

import com.example.coinalculator.ui.calculator.domain.CalculatorModel

class CalcStateMapper () {
    fun toCoinCalState(coinCalculator: CalculatorModel) : CoinCalState {
        return CoinCalState(
            name = coinCalculator.name,
            price = coinCalculator.price
        )
    }
}