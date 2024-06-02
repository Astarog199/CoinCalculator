package com.example.coinalculator.ui.dashboard.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class CoinModel (
 @Json(name = "bitcoin") val coin: Bitcoin
)

@JsonClass(generateAdapter = true)
data class Bitcoin (
    @Json(name = "usd") val usd: Long,
//    @Json(name = "usd_market_cap") val usd_market_cap: Double
)


