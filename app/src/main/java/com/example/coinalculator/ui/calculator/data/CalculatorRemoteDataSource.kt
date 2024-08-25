package com.example.coinalculator.ui.calculator.data

import com.example.coinalculator.ui.calculator.data.models.CoinRub
import com.example.coinalculator.ui.calculator.data.models.RubDTO
import retrofit2.await

class CalculatorRemoteDataSource (
    private val listCoinApi: CalculatorApiService
){
    suspend fun getRub(): RubDTO {
        return try {
            listCoinApi.getRub("usd", "rub").await()
        } catch (e: Exception) {
            println("Error while getting query result from server: $e")
            RubDTO(CoinRub(0f))
        }
    }
}