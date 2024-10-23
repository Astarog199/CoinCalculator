package com.example.coinalculator.ui.calculator.data

import android.util.Log
import com.example.coinalculator.ui.calculator.data.models.dto.Rub
import com.example.coinalculator.ui.calculator.data.models.dto.RubDTO
import com.google.firebase.crashlytics.FirebaseCrashlytics
import retrofit2.await
import javax.inject.Inject

class CalculatorRemoteDataSource @Inject constructor(
    private val api: CalculatorApiService
){
    suspend fun getRub(): RubDTO {
        return try {
            api.getRub("usd", "rub").await()
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
            Log.d("ERROR", "CalculatorRemoteDataSource: $e")
            RubDTO(
                Rub(0f)
            )
        }
    }
}