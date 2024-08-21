package com.example.coinalculator.ui.calculator.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ModelDto (
    @Json(name = "bitcoin") val coinUsd: CoinUsd
)

@JsonClass(generateAdapter = true)
data class CoinUsd (
    @Json(name = "usd") val usd: Long
)