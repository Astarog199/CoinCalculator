package com.example.coinalculator.ui.calculator.data.models.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RubDTO(
    @Json(name = "usd") val price: com.example.coinalculator.ui.calculator.data.models.dto.Rub
)