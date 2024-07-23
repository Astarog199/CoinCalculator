package com.example.coinalculator.ui.Coins.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FilterCoinsListUseCase(private val coinsRepository: CoinsRepository) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private lateinit var filterCoin: Flow<List<Coin>>



    suspend  fun searchCoin(query: String) = suspendCoroutine {
        scope.launch {
            filterCoin = coinsRepository.consumeCoins()
                .map { value: List<Coin> ->
                    value.filter { coin ->
                        coin.name.contains(query)
                    }
                }
            it.resume(filterCoin)
        }
    }
}