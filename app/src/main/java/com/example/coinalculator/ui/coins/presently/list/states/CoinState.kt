package com.example.coinalculator.ui.coins.presently.list.states

data class CoinState (
    val id: Int = 0,
    val name: String = "",
    val market: String = "",
    val price: String = "",
    val isFavorite: Boolean = false
)