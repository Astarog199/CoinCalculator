package com.example.coinalculator.ui.coins.presently.card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinalculator.ui.coins.domain.AddFavoriteUseCase
import com.example.coinalculator.ui.coins.domain.ConsumeCoinCard
import com.example.coinalculator.ui.coins.presently.card.states.CoinCardScreenStates
import com.example.coinalculator.ui.coins.presently.card.states.CoinDetailsStatesMapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinCardViewModel(
    private val consumeCoinCard: ConsumeCoinCard,
    private val coinDetailsStatesMapper: CoinDetailsStatesMapper,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val productId: String
) : ViewModel() {
    private val _state = MutableStateFlow(CoinCardScreenStates())
    val state: StateFlow<CoinCardScreenStates> = _state.asStateFlow()


    fun loadCoinCard(){
        consumeCoinCard(productId)
            .map { coins ->
                coinDetailsStatesMapper.toCoinCardStates(coins)
            }
            .onStart {
                _state.update { coinCardScreenStates -> coinCardScreenStates.copy(isLoading = true) }
            }
            .onEach { coinCardStates ->
                _state.update {
                    coinCardScreenStates -> coinCardScreenStates.copy(
                        isLoading = false,
                        coin = coinCardStates
                    )
                }
            }
            .catch {
                cheduleRefresh()

                _state.update { coinCardScreenStates ->
                    coinCardScreenStates.copy(hasError = true)
                }
            }
            .launchIn(viewModelScope)
    }

    private suspend fun cheduleRefresh() {
        viewModelScope.launch {
            delay(5000)
            clearError()
            loadCoinCard()
        }
    }

    fun clearError(){
        _state.update { screenState -> screenState.copy(hasError = false)  }
    }

     suspend fun changeFavorite() {
        _state.update { coinState -> coinState.copy(coin = coinState.coin.copy(isFavorite = true))

        }
         addFavoriteUseCase(CoinDetailsStatesMapper().toCoinDetails(_state.value.coin))
    }
}