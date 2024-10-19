package com.example.coinalculator.ui.calculator.domain

import kotlinx.serialization.Serializable

@Serializable
data class DomainEntity (
    val symbol : String,
    val price: Float,
)