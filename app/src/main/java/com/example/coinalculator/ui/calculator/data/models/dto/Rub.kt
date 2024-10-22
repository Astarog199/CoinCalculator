package com.example.coinalculator.ui.calculator.data.models.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Rub (
    @Json(name = "rub") val usd: Float
)