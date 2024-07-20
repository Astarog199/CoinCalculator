package com.example.coinalculator.ui.CoinsList.domain

import com.example.coinalculator.ui.CoinsList.data.CoinsListRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ConsumeDashboardUseCase(private val repositoryImpl: CoinsListRepositoryImpl) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private lateinit var listCoin: Flow<List<Coin>>

    suspend fun consumeCoin() = suspendCoroutine {
        scope.launch {
            listCoin = repositoryImpl.consumeCoins()
        }
        it.resume(listCoin)
    }
}