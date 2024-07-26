package com.example.coinalculator.ui.Coins.presently.model

import com.example.coinalculator.ui.Coins.presently.CoinVO

data class CoinListState (
    val isLoading: Boolean = false,
    val coinsList: List<CoinVO> = mutableListOf(),
    val hasError: Boolean = false,
)