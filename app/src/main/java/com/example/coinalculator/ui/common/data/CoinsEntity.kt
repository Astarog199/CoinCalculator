package com.example.coinalculator.ui.common.data


import kotlinx.serialization.Serializable

@Serializable
data class CoinsEntity(
     val id: Int,
     val name: String,
     val market: String,
     val price: String
)