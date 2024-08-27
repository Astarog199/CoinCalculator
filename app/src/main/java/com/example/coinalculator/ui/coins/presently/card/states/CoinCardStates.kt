package com.example.coinalculator.ui.coins.presently.card.states

data class CoinCardStates (
//    val id: Int = 0,
    val name: String = "",
    val image: String = "",
    val price: String ="",
    val change24h: Float = 0f,
    val isFavorite: Boolean = false
)