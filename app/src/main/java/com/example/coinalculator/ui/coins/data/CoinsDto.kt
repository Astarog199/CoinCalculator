package com.example.coinalculator.ui.coins.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoinsDto(
    @Json(name = "market") val market: String?,
    @Json(name = "symbol") val symbol: String?,
    @Json(name = "index_id") val index_id: String?,
    @Json(name = "price") val price: String,
    @Json(name = "price_percentage_change_24h") val price_percentage_change_24h: Float,
    @Json(name = "contract_type") val contract_type: String,
    @Json(name = "index") val index: Float?,
    @Json(name = "basis") val basis: Float,
    @Json(name = "spread") val spread:Float?,
    @Json(name = "funding_rate") val funding_rate: Float,
    @Json(name = "open_interest") val open_interest: Float?,
    @Json(name = "volume_24h") val volume_24h: Float?,
    @Json(name = "last_traded_at") val last_traded_at: Long?,
    @Json(name = "expired_at") val expired_at: String? = null
)

