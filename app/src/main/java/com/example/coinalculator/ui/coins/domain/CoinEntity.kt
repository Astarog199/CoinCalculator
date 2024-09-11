package com.example.coinalculator.ui.coins.domain

class CoinEntity(
    val name: String,
    val image: String,
    val price: Float,
    val pricePercentageChange24h:Float,
    val priceChange24h: Float,
    val marketCap: Long,
    val marketCapRank: Int,
    val totalVolume: Long,
    val isFavorite: Boolean
)
