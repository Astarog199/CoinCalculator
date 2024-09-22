package com.example.coinalculator.ui.favorite.presently

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coinalculator.ui.favorite.domain.ConsumeFavoritesUseCase
import com.example.coinalculator.ui.favorite.presently.states.FavoriteScreenStatesMapper

class FavoriteViewModelFactory(
    private val favoriteScreenStatesMapper: FavoriteScreenStatesMapper,
    private val consumeFavorietesUseCase: ConsumeFavoritesUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteViewModel(
            favoriteScreenStatesMapper = favoriteScreenStatesMapper,
            consumeFavoritesUseCase = consumeFavorietesUseCase
        ) as T
    }
}