package com.example.coinalculator.ui.favorite.presently

import androidx.lifecycle.ViewModelProvider
import com.example.coinalculator.ui.favorite.presently.states.FavoriteStates

object FeatureServiceLocator {

    fun provideFavoriteViewModelFactory() : ViewModelProvider.Factory {
        return FavoriteViewModelFactory()
    }

    private fun provideFavoriteScreenStatesMapper() : FavoriteStates {
        return FavoriteStates()
    }
}