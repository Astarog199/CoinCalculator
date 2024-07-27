package com.example.coinalculator.ui.Coins.presently.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinalculator.ui.Coins.domain.ConsumeCoinsUseCase
import com.example.coinalculator.ui.Coins.domain.FilterCoinsListUseCase
import com.example.coinalculator.ui.Coins.presently.CoinVO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinViewModel(
    private val coinStateMapper: CoinStateMapper,
    private val consumeCoinsUseCase: ConsumeCoinsUseCase,
    private val filterCoinsListUseCase: FilterCoinsListUseCase
): ViewModel() {
    private val _coinState = MutableStateFlow(CoinListState())
    val coinState: StateFlow<CoinListState> = _coinState.asStateFlow()

    private var _filter = MutableStateFlow<List<CoinState>>(listOf())
    val filter: StateFlow<List<CoinState>> = _filter.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.IO)

    fun loadCoins() {
        consumeCoinsUseCase()
            .map { coins ->
                coins.map(coinStateMapper::toCoinState)
            }
            .onStart {
                _coinState.update { list ->list.copy(isLoading = true) }
            }
            .onEach { coinsState ->
                _coinState.update {
                    coin -> coin.copy(isLoading = false, coinsList = coinsState)
                }
            }
            .launchIn(viewModelScope)
    }

    fun searchCoin(arg: String) {
        scope.launch {
            _filter.value = filterCoinsListUseCase.searchCoin(_coinState.value.coinsList, arg)
        }
    }

    fun errorShown() {
        _coinState.update { screenState -> screenState.copy(hasError = false) }
    }
}