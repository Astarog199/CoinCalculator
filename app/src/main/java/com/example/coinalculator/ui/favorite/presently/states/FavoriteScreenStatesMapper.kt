package com.example.coinalculator.ui.favorite.presently.states

import com.example.coinalculator.ui.favorite.domain.Favorite

class FavoriteScreenStatesMapper {
    fun toFavotiteStates(favorite: Favorite) : FavoriteStates {
        return FavoriteStates(
            id = favorite.id,
            name = favorite.name,
            market = favorite.market,
            price = favorite.price,
            isFavorite = favorite.isFavorite
        )
    }
}