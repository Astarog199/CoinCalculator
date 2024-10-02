package com.example.coinalculator.ui.calculator.presently

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinalculator.ui.calculator.domain.ConsumeCalculatorUseCase
import com.example.coinalculator.ui.calculator.presently.states.CalcStateMapper
import com.example.coinalculator.ui.calculator.presently.states.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

class CalculatorViewModel(
    private val consumeCoinsUseCase: ConsumeCalculatorUseCase,
    private val calcStateMapper: CalcStateMapper
) : ViewModel() {
    private var amountOfFiatCurrency = 1000f
    private val _items = MutableStateFlow(ScreenState())
    val items: StateFlow<ScreenState> = _items.asStateFlow()
    private var sizeValue = 0

    fun loadItems() {
        consumeCoinsUseCase()
            .map { coins ->
                sizeValue = coins.size

                coins.map { coin ->
                    if (coin.name == "btc") amountOfFiatCurrency = coin.price

                    calcStateMapper.toCoinCalState(coin)
                        .copy(value = amountOfFiatCurrency)
                }
            }
            .onStart {
                _items.update { list -> list.copy(isLoading = true) }
            }
            .onEach { coins ->
                _items.update { state ->
                    state.copy(isLoading = false, coinsList = coins)
                }

            }
            .catch {
                _items.update { screenState ->
                    screenState.copy(hasError = true)
                }
            }
            .launchIn(viewModelScope)
    }

    fun errorShown() {
        _items.update { screenState -> screenState.copy(hasError = false) }
    }

    fun changeVolume(arg: Float) {
        amountOfFiatCurrency = arg

        _items.update { state ->
            state.copy(coinsList = state.coinsList.map { coin ->
                coin.copy(value = amountOfFiatCurrency)
            })
        }
    }

    fun showInstructions() : Boolean {
        return sizeValue < 4
    }
}