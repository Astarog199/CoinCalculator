package com.example.coinalculator.ui.dashboard.data

import com.example.coinalculator.ui.dashboard.domain.DashboardRepository
import retrofit2.await

class DashboardRepositoryImpl(private val coinApi: SearchApi):DashboardRepository {
        override suspend fun getCoin(): CoinModel{
            return coinApi.getCoinApi("bitcoin", "usd").await()
    }

    override suspend fun getList() : List<ElementList>{
        return coinApi.getCoinList().await()
    }
}
