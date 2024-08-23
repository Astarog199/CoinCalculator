package com.example.coinalculator.ui.calculator.data

import com.example.coinalculator.ui.calculator.data.models.CoinCalculatorDto
import com.example.coinalculator.ui.calculator.data.models.RubDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface CalculatorApiService {

    @Headers("X-API_KEY: CG-MZ9cPh1mmN1AUaGQbURE1ZeH&vs_currency=usd")
    @GET("simple/price")
    fun getRub(
        @Query("ids") ids: String,
        @Query("vs_currencies") vsCurrencies: String
    ): Call<RubDTO>
}
