package com.example.coinalculator.ui.coins.presently.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinalculator.ui.coins.domain.CoinEntity
import com.example.coinalculator.ui.coins.domain.ConsumeCoinListUseCase
import com.example.coinalculator.ui.coins.domain.FilterCoinsListUseCase
import com.example.coinalculator.ui.coins.presently.list.states.CoinListState
import com.example.coinalculator.ui.coins.presently.list.states.CoinState
import com.example.coinalculator.ui.coins.presently.list.states.CoinStateMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinListViewModel(
    private val coinStateMapper: CoinStateMapper,
    private val consumeCoinsUseCase: ConsumeCoinListUseCase,
    private val filterCoinsListUseCase: FilterCoinsListUseCase
) : ViewModel() {
    private val _coinState = MutableStateFlow(CoinListState())
    val coinState: StateFlow<CoinListState> = _coinState.asStateFlow()

    private var _filter = MutableStateFlow<List<CoinState>>(listOf())
    val filter: StateFlow<List<CoinState>> = _filter.asStateFlow()

    private var listV: List<CoinEntity> = mutableListOf()

    fun loadCoins() {
        consumeCoinsUseCase()
            .filter { it.isNotEmpty() }
            .map { coins ->
                listV = coins
                coins.map(coinStateMapper::toCoinState)
            }
            .onStart {
                _coinState.update { list -> list.copy(isLoading = true) }
            }
            .onEach { coinsState ->
                _coinState.update { coin ->
                    when {
                        coin.filter -> {
                            coin.copy(isLoading = false, coinsList = _filter.value)
                        }

                        else -> coin.copy(isLoading = false, coinsList = coinsState)
                    }
                }
            }
            .catch {
                _coinState.update { screenState ->
                    screenState.copy(hasError = true)
                }
            }
            .launchIn(viewModelScope)
    }

    fun searchCoin(arg: String) {
        val newStr = makeWithCapitalLetter(arg)

        viewModelScope.launch {
            when {
                arg.isNotEmpty() -> {
                    _filter.value = filterCoinsListUseCase.invoke(newStr, listV).map(coinStateMapper::toCoinState)

                    filterCheck()
                }

                else -> {
                    _coinState.update { coin ->
                        coin.copy(filter = false)
                    }
                }
            }
        }
    }

    private fun filterCheck() {
        if (_filter.value.isNotEmpty()) {
            _coinState.update { coin ->
                coin.copy(filter = true)
            }
        } else {
            _coinState.update { coin ->
                coin.copy(filter = false)
            }
        }
    }

    private fun makeWithCapitalLetter(arg: String): String {
        var newStr = ""
        val str = arg.split(" ")
        for (i in str) {
            newStr += i.replaceFirstChar(Char::uppercaseChar) + " "
        }

        return newStr.trim()
    }

    fun resetView() {
        _coinState.update { coin ->
            coin.copy(filter = false)
        }
    }

    fun errorShown() {
        _coinState.update { screenState -> screenState.copy(hasError = false) }
    }
}