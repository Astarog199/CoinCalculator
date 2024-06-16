package com.example.coinalculator.ui.dashboard.presently

import androidx.lifecycle.ViewModel
import com.example.coinalculator.ui.dashboard.data.ElementList
import com.example.coinalculator.ui.dashboard.domain.ConsumeDashboardUseCase
import com.example.coinalculator.ui.dashboard.domain.FilterCoinsListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class DashboardViewModel(
    private val consumeDashboardUseCase: ConsumeDashboardUseCase,
    private val filterCoinsListUseCase: FilterCoinsListUseCase
) : ViewModel() {
    private var _coin = MutableStateFlow<List<ElementList>>(listOf())
    val coin: StateFlow<List<ElementList>> = _coin.asStateFlow()

    private var _filter = MutableStateFlow<List<ElementList>>(listOf())
    val filter: StateFlow<List<ElementList>> = _filter.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        consumeCoin()
    }

    private fun consumeCoin() {
        scope.launch {
            _coin.value = consumeDashboardUseCase.consumeCoin()
        }
    }

    fun searchCoin(arg: String) {
        scope.launch {
            _filter.value = filterCoinsListUseCase.searchCoin(arg)
        }
    }
}
