package com.example.coinalculator.ui.dashboard.domain

import com.example.coinalculator.ui.dashboard.data.CoinDto
import com.example.coinalculator.ui.dashboard.data.DashboardRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ConsumeDashboardUseCase(private val repositoryImpl: DashboardRepositoryImpl) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private lateinit var listCoin: Flow<List<Coin>>


//    suspend fun consumeCoin() = suspendCoroutine {
//        scope.launch {
//            listCoin = repositoryImpl.getList()
//                .filter { coin ->
//                    coin.market!!.contains("Binance")
//                }
//            it.resume(listCoin)
//        }
//    }

    suspend fun consumeCoin() = suspendCoroutine {
        scope.launch {
            listCoin = repositoryImpl.consumeCoins()
        }
        it.resume(listCoin)
    }

}