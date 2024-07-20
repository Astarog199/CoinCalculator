package com.example.coinalculator.ui.CoinsList.domain

import com.example.coinalculator.ui.CoinsList.data.CoinsListDto
import com.example.coinalculator.ui.CoinsList.data.CoinsListRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class FilterCoinsListUseCase(private val repositoryImpl: CoinsListRepositoryImpl) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var filterCoin: List<CoinsListDto> = mutableListOf()

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