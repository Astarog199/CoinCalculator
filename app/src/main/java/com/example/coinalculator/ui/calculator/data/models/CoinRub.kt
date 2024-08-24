package com.example.coinalculator.ui.calculator.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class CoinRub (
    @Json(name = "rub") val usd: Float
)