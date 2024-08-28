package com.example.coinalculator.ui.coins.domain

class Coin(
    val name: String,
    val image: String,
    val price: String,
    val pricePercentageChange24h:Float,
    val priceChange24h: Float,
    val marketCap: Long,
    val marketCapRank: Int,
    val totalVolume: Long,
    val isFavorite: Boolean
)
