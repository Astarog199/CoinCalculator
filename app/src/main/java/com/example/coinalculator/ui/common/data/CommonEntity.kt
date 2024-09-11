package com.example.coinalculator.ui.common.data


import kotlinx.serialization.Serializable

@Serializable
data class CommonEntity(
     val name: String,
     val image: String,
     val price: Float,
     val pricePercentageChange24h : Float,
     val priceChange24h: Float,
     val marketCap: Long,
     val marketCapRank: Int,
     val totalVolume: Long,
     val isFavorite: Boolean = false
)