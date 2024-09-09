package com.example.coinalculator.ui.favorite.domain

data class FavoriteEntity(
    val name: String,
    val image: String,
    val price: String,
    val change24h : Float,
    val isFavorite: Boolean
)
