package com.example.coinalculator.ui.calculator.presently.states



data class ScreenState (
    val isLoading: Boolean = false,
    val coinsList: List<CoinCalState> = mutableListOf(),
    val hasError: Boolean = false,
)