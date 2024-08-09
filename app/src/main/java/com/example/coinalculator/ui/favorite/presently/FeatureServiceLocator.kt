package com.example.coinalculator.ui.favorite.presently

import androidx.lifecycle.ViewModelProvider
import com.example.coinalculator.ServiceLocator
import com.example.coinalculator.ui.favorite.presently.states.FavoriteScreenStatesMapper
import com.example.coinalculator.ui.favorite.presently.states.FavoriteStates

object FeatureServiceLocator {

    fun provideFavoriteViewModelFactory() : ViewModelProvider.Factory {
        return FavoriteViewModelFactory(
            consumeFavorietesUseCase = ServiceLocator.provideConsumeFavoriteList(),
            favoriteScreenStatesMapper = provideFavoriteScreenStatesMapper()
        )
    }

    private fun provideFavoriteScreenStatesMapper() : FavoriteScreenStatesMapper {
        return FavoriteScreenStatesMapper()
    }
}