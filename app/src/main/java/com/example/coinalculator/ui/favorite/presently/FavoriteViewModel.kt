package com.example.coinalculator.ui.favorite.presently

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinalculator.ui.favorite.domain.ConsumeFavoritesUseCase
import com.example.coinalculator.ui.favorite.presently.states.FavoriteScreenStates
import com.example.coinalculator.ui.favorite.presently.states.FavoriteScreenStatesMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

class FavoriteViewModel(
    private val favoriteScreenStatesMapper: FavoriteScreenStatesMapper,
    private val consumeFavoritesUseCase: ConsumeFavoritesUseCase,
) : ViewModel() {

    private val _favoriteState = MutableStateFlow(FavoriteScreenStates())
    val favoriteState: StateFlow<FavoriteScreenStates> = _favoriteState.asStateFlow()

    fun loadCoins() {
        consumeFavoritesUseCase()
            .map { favorite ->
                favorite.map(favoriteScreenStatesMapper::toFavotiteStates)
            }
            .onStart {
                _favoriteState.update { list -> list.copy(isLoading = true) }
            }
            .onEach { coins ->
                _favoriteState.update { state ->
                    state.copy(isLoading = false, favoriteList = coins)
                }
            }
            .catch {
                _favoriteState.update { screenState ->
                    screenState.copy(hasError = true)
                }
            }
            .launchIn(viewModelScope)
    }

    fun errorShown() {
        _favoriteState.update { screenState -> screenState.copy(hasError = false) }
    }
}