package com.example.coinalculator.ui.dashboard.domain

import com.example.coinalculator.ui.dashboard.data.CoinModel
import com.example.coinalculator.ui.dashboard.data.DashboardRepositoryImpl
import com.example.coinalculator.ui.dashboard.data.ElementList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ConsumeDashboardUseCase(private val repositoryImpl: DashboardRepositoryImpl) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var listCoin: List<ElementList> = mutableListOf()
    private var filterCoin: List<ElementList> = listCoin

    suspend fun consumeCoin() = suspendCoroutine {
        scope.launch {
            listCoin = repositoryImpl.getList()
                .filter { coin ->
                    coin.market!!.contains("Binance")
                }
            it.resume(listCoin)
        }
    }


    suspend fun searchCoin(query: String) = suspendCoroutine {
        scope.launch {
            filterCoin.filter { coin ->
                coin.index_id!!.contains(query)
            }
            it.resume(filterCoin)
        }
    }
}