package com.example.coinalculator.ui.dashboard.presently

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.coinalculator.ui.dashboard.domain.Coin
import com.example.coinalculator.ui.dashboard.domain.ConsumeDashboardUseCase
import com.example.coinalculator.ui.dashboard.domain.FilterCoinsListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val consumeDashboardUseCase: ConsumeDashboardUseCase,
    private val filterCoinsListUseCase: FilterCoinsListUseCase,
    private val coinVOMapper: CoinVOMapper
) : ViewModel() {
    private var _coin = MutableStateFlow<List<CoinVO>>(listOf())
    val coin: StateFlow<List<CoinVO>> = _coin.asStateFlow()

    private var _filter = MutableStateFlow<List<CoinVO>>(listOf())
    val filter: StateFlow<List<CoinVO>> = _filter.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        consumeCoin()
    }

    private fun consumeCoin() {
        scope.launch {
//            consumeDashboardUseCase.consumeCoin().map { value: List<Coin> ->
//                value.map { coin->
//                    _coin.value = listOf(coinVOMapper.toCoinVO(coin))
//                }
//            }
        }
    }

    fun searchCoin(arg: String) {
        scope.launch {
//            _filter.value = filterCoinsListUseCase.searchCoin(arg)
        }
    }
}
