package com.example.coinalculator.ui.Coins.data


import kotlinx.serialization.Serializable

@Serializable
data class CoinsEntity (
     val name: String = "",
     val market: String,
     val price: String
)

