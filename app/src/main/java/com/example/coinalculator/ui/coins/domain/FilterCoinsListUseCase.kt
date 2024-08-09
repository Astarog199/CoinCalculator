package com.example.coinalculator.ui.coins.domain

import com.example.coinalculator.ui.coins.presently.coins.model.CoinState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FilterCoinsListUseCase() {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var _filterCoin: List<CoinState> = mutableListOf()
    private var filterCoin: List<CoinState> = mutableListOf()
    private var _query = ""

    suspend operator fun invoke() = suspendCoroutine {
        scope.launch {
            filterCoin = _filterCoin
                .filter { coin ->
                    coin.name.contains(_query)
                }
            it.resume(filterCoin)
        }
    }

     fun searchCoin(coins: List<CoinState>, query: String) {
        _filterCoin = coins
        _query = query
    }
}
