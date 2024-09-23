package com.example.coinalculator.ui.coins.presently.list.states

data class CoinState (
    val name: String = "",
    val image: String = "",
    val price: Float = 0f,
    val priceChange24h: Float = 0f,
    val pricePercentageChange24h: Float = 0f,
    val isFavorite: Boolean = false
)