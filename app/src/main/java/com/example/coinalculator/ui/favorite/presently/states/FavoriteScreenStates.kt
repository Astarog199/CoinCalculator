package com.example.coinalculator.ui.favorite.presently.states

data class FavoriteScreenStates (
    val isLoading: Boolean = false,
    val favoriteList: List<FavoriteStates> = mutableListOf(),
    val hasError: Boolean = false,
)