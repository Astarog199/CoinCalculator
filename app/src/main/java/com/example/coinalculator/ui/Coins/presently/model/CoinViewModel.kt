package com.example.coinalculator.ui.Coins.presently.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinalculator.ui.Coins.domain.ConsumeCoinsUseCase
import com.example.coinalculator.ui.Coins.domain.FilterCoinsListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class CoinViewModel(
    private val coinStateMapper: CoinStateMapper,
    private val consumeCoinsUseCase: ConsumeCoinsUseCase,
    private val filterCoinsListUseCase: FilterCoinsListUseCase
): ViewModel() {
    private val _coinState = MutableStateFlow(CoinState())
    val coinState: StateFlow<CoinState> = _coinState.asStateFlow()

    fun loadCoins() {
        consumeCoinsUseCase()
            .map { promos ->
                promos.map(coinStateMapper::toCoinState)
            }
            .onEach { coinsState ->
                coinsState.map { coinState->
                    _coinState.update {
                        coinState
                    }
                }


//                view.showProgress()
//                view.showCoins(coinVOMapper)
            }
            .launchIn(viewModelScope)
    }
}