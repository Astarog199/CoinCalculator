package com.example.coinalculator.ui.favorite.domain

data class Favorite(
    val id: Int,
    val name: String,
    val market: String,
    val price: String,
    val change24h : Float,
    val isFavorite: Boolean
)