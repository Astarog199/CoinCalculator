package com.example.coinalculator.ui.calculator.domain

import kotlinx.serialization.Serializable

@Serializable
data class DomainEntity (
    val name : String,
    val price: Float,
)