package com.example.coinalculator.ui.coins.presently.list.states


data class CoinListState (
    val isLoading: Boolean = false,
    val filter: Boolean = false,
    val coinsList: List<CoinState> = mutableListOf(),
    val hasError: Boolean = false,
)