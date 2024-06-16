package com.example.coinalculator.ui.dashboard.domain

import com.example.coinalculator.ui.dashboard.data.DashboardRepositoryImpl
import com.example.coinalculator.ui.dashboard.data.ElementList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FilterCoinsListUseCase(private val repositoryImpl: DashboardRepositoryImpl) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var filterCoin: List<ElementList> = mutableListOf()

    suspend fun searchCoin(query: String) = suspendCoroutine {
        scope.launch {
            filterCoin = repositoryImpl.getList()
            .filter { coin ->
                coin.market!!.contains("Binance") &&
                coin.index_id!!.contains(query)
            }
            it.resume(filterCoin)
        }
    }
}