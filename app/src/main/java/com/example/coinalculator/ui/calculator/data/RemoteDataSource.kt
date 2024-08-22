package com.example.coinalculator.ui.calculator.data

import com.example.coinalculator.ui.calculator.data.models.DTO
import retrofit2.await

class RemoteDataSource (
    private val listCoinApi: CalculatorApiService
){
    suspend fun getBitcoin(): DTO {
        return listCoinApi.getCoin("bitcoin", "usd").await()
    }
}