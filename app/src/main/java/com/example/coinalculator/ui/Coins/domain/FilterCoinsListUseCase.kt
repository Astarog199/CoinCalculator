package com.example.coinalculator.ui.Coins.domain

import com.example.coinalculator.ui.Coins.presently.CoinVO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FilterCoinsListUseCase(private val coinsRepository: CoinsRepository) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var filterCoin: List<CoinVO> = mutableListOf()

    suspend fun searchCoin(coins: List<CoinVO>, query: String) = suspendCoroutine {
        scope.launch {
            filterCoin = coins
                .filter { coin ->
                    coin.name.contains(query)
                }
            it.resume(filterCoin)
        }

    }
}
