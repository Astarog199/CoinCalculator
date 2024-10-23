package com.example.coinalculator.ui.coins.domain

class CoinEntity(
    val symbol: String,
    val name: String,
    val image: String,
    val price: Float,
    val pricePercentageChange24h:Float,
    val priceChange24h: Float,
    val marketCap: Long,
    val marketCapRank: Int,
    val totalVolume: Long,
    val high24h: Float,
    val low24h: Float,
    val circulatingSupply: Float,
    val totalSupply: Float,
    val maxSupply: Float?,
    val isFavorite: Boolean
)
