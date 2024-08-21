package com.example.coinalculator.ui.calculator.presently

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinalculator.ui.calculator.data.RemoteDataSource
import com.example.coinalculator.ui.calculator.domain.CoinCalculator
import com.example.coinalculator.ui.calculator.domain.ConsumeCoinsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CalculatorViewModel (
    private val consumeCoinsUseCase: ConsumeCoinsUseCase
) : ViewModel() {
    private val _items = MutableStateFlow<List<CoinCalculator>>(listOf())
    val items: StateFlow<List<CoinCalculator>> = _items.asStateFlow()

    init {
        loadItems()
    }

    fun loadItems() {
        consumeCoinsUseCase().onEach { coin->
            _items.value = coin
        }
            .launchIn(viewModelScope)
    }
}