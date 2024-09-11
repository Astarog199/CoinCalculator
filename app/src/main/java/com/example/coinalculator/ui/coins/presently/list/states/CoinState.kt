package com.example.coinalculator.ui.coins.presently.list.states

data class CoinState (
    val name: String = "",
    val image: String = "",
    val price: Float = 0f,
    val isFavorite: Boolean = false
)