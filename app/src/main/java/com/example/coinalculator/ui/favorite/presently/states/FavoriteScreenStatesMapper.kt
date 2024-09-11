package com.example.coinalculator.ui.favorite.presently.states

import com.example.coinalculator.ui.favorite.domain.FavoriteEntity

class FavoriteScreenStatesMapper {
    fun toFavotiteStates(favorite: FavoriteEntity) : FavoriteStates {
        return FavoriteStates(
            name = favorite.name,
            image = favorite.image,
            price = favorite.price,
            isFavorite = favorite.isFavorite
        )
    }
}