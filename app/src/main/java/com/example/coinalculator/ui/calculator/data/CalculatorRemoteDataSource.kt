package com.example.coinalculator.ui.calculator.data

import com.example.coinalculator.ui.calculator.data.models.dto.Rub
import com.example.coinalculator.ui.calculator.data.models.dto.RubDTO
import retrofit2.await
import javax.inject.Inject

class CalculatorRemoteDataSource @Inject constructor(
    private val api: CalculatorApiService
){
    suspend fun getRub(): RubDTO {
        return try {
            api.getRub("usd", "rub").await()
        } catch (e: Exception) {
            println("Error while getting query result from server: $e")
            RubDTO(
                Rub(0f)
            )
        }
    }
}