package com.example.coinalculator.ui.coins.domain

import com.example.coinalculator.ui.coins.presently.list.states.CoinState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FilterCoinsListUseCase() {
    private val scope = CoroutineScope(Dispatchers.IO)

    suspend operator fun invoke(coins: List<CoinState>, query: String) = suspendCoroutine {
        scope.launch {
           val filterCoins = coins
                .filter { coin ->
                    coin.name.contains(query)
                }
            it.resume(filterCoins)
        }
    }
}
