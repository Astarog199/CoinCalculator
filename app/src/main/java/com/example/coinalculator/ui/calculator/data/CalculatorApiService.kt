package com.example.coinalculator.ui.calculator.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface CalculatorApiService {
    @GET("simple/price")
    fun getCoin(
        @Query("ids") ids: String,
        @Query("vs_currencies") vsCurrencies: String
    ): Call<ModelDto>
}