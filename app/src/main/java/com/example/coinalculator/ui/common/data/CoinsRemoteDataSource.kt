package com.example.coinalculator.ui.common.data

import retrofit2.await

class CoinsRemoteDataSource(
    private val coinApi: CoinsApiService,
) {
     suspend fun getList(): List<CoinsDto> {
        return coinApi.getCoinList().await()
    }
}