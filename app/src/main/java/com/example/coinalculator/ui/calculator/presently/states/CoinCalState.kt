package com.example.coinalculator.ui.calculator.presently.states

data class CoinCalState (
    val name : String = "",
    val price : Float = 0f,
    val value: Float = 0f
){
    fun getPriceValue() : Float {
        return value / price
    }
}