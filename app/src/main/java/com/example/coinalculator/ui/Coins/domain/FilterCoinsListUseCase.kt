package com.example.coinalculator.ui.Coins.domain

import com.example.coinalculator.ui.Coins.data.CoinsDto
import com.example.coinalculator.ui.Coins.data.CoinsRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class FilterCoinsListUseCase(private val repositoryImpl: CoinsRepositoryImpl) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var filterCoin: List<CoinsDto> = mutableListOf()

//    suspend fun searchCoin(query: String) = suspendCoroutine {
//        scope.launch {
//            filterCoin = repositoryImpl.getList()
//            .filter { coin ->
//                coin.market!!.contains("Binance") &&
//                coin.index_id!!.contains(query)
//            }
//            it.resume(filterCoin)
//        }
//    }
}