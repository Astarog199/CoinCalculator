package com.example.coinalculator.ui.coins.presently.card.states

data class CoinCardScreenStates (
    val isLoading: Boolean = false,
    val filter: Boolean = false,
    val coin: CoinCardStates = CoinCardStates(),
    val hasError: Boolean = false,
)