package com.example.coinalculator.ui.coins.presently.card.states

data class CoinCardStates (
    val symbol: String = "",
    val name: String = "",
    val image: String = "",
    val price: Float = 0f,
    val pricePercentageChange24h: Float = 0f,
    val priceChange24h: Float =0f,
    val marketCap: Long = 0,
    val marketCapRank: Int = 0,
    val totalVolume: Long = 0,
    val isFavorite: Boolean = false
)