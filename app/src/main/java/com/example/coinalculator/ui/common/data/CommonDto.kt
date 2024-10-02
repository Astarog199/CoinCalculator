package com.example.coinalculator.ui.common.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoinsDto(
    @Json(name = "id") val id: String,
    @Json(name = "symbol") val symbol: String,
    @Json(name = "name") val name: String,
    @Json(name = "image") val image: String,
    @Json(name = "current_price") val currentPrice: Float,
    @Json(name = "market_cap") val marketCap: Long,
    @Json(name = "market_cap_rank") val marketCapRank: Int,
    @Json(name = "fully_diluted_valuation") val fullyDilutedValuation: Float,
    @Json(name = "total_volume") val totalVolume: Long,
    @Json(name = "high_24h") val high24h: Float,
    @Json(name = "low_24h") val low24h: Float,
    @Json(name = "price_change_24h") val priceChange24h: Float,
    @Json(name = "price_change_percentage_24h") val priceChangePercentage24h: Float,
    @Json(name = "market_cap_change_24h") val marketCapChange24h: Float,
    @Json(name = "market_cap_change_percentage_24h") val marketCapChangePercentage24h: Float,
    @Json(name = "circulating_supply") val circulatingSupply: Float,
    @Json(name = "total_supply") val totalSupply: Float,
    @Json(name = "max_supply") val maxSupply: Float?,
    @Json(name = "ath") val ath: Float,
    @Json(name = "ath_change_percentage") val athChangePercentage: Float,
    @Json(name = "ath_date") val athDate: String,
    @Json(name = "atl") val atl: Float,
    @Json(name = "atl_change_percentage") val atlChangePercentage: Float,
    @Json(name = "atl_date") val atlDate: String,
    @Json(name = "roi") val roi: Roi? = null,
    @Json(name = "last_updated") val lastUpdated: String
)

@JsonClass(generateAdapter = true)
data class Roi(
    val times: Float,
    val currency: String,
    val percentage: Float
)

@JsonClass(generateAdapter = true)
data class RubDTO (
    @Json(name = "usd") val price: Rub
)

@JsonClass(generateAdapter = true)
data class Rub (
    @Json(name = "rub") val usd: Float
)
