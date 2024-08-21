package com.example.coinalculator.ui.calculator.data

import retrofit2.await

class RemoteDataSource (
    private val listCoinApi: CalculatorApiService
){
    suspend fun getCoins(): ModelDto {
        return listCoinApi.getCoin("bitcoin", "usd").await()
    }
}