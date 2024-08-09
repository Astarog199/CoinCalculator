package com.example.coinalculator.ui.favorite.presently

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinalculator.ui.favorite.domain.ConsumeFavorietesUseCase
import com.example.coinalculator.ui.favorite.presently.states.FavoriteScreenStates
import com.example.coinalculator.ui.favorite.presently.states.FavoriteScreenStatesMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

class FavoriteViewModel(
    private val favoriteScreenStatesMapper: FavoriteScreenStatesMapper,
    private val consumeFavorietesUseCase: ConsumeFavorietesUseCase,
) : ViewModel() {

    private val _favoriteState = MutableStateFlow(FavoriteScreenStates())
    val favoriteState: StateFlow<FavoriteScreenStates> = _favoriteState.asStateFlow()

    suspend fun loadCoins() {
        consumeFavorietesUseCase()
            .map { favorite ->
                favorite.map(favoriteScreenStatesMapper::toFavotiteStates)
            }
            .onStart {
                _favoriteState.update { list -> list.copy() }
            }
            .onEach { state ->
                _favoriteState.update { coin ->
                    coin.copy(isLoading = false, favoriteList = state)
                }
            }
            .launchIn(viewModelScope)
    }

    fun errorShown() {
        _favoriteState.update { screenState -> screenState.copy(hasError = false) }
    }
}