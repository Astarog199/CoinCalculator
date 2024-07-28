package com.example.coinalculator.ui.coins.data


import kotlinx.serialization.Serializable

@Serializable
data class CoinsEntity(
     val id: Int,
     val name: String,
     val market: String,
     val price: String
)