package com.example.coinalculator.ui.common.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CommonApiService {
    @Headers("X-API_KEY: CG-MZ9cPh1mmN1AUaGQbURE1ZeH&vs_currency=usd")
    @GET("coins/markets?vs_currency=usd")
    fun getCoinList(): Call<List<CoinsDto>>
}