package com.example.coinalculator.ui.calculator.data

import com.example.coinalculator.ui.calculator.data.models.RubDTO
import retrofit2.await

class RemoteDataSource (
    private val listCoinApi: CalculatorApiService
){
    suspend fun getRub(): RubDTO {
        return listCoinApi.getRub("usd", "rub").await()
    }
}