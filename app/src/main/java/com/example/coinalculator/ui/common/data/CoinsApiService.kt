package com.example.coinalculator.ui.common.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinsApiService {
    @GET("coins/markets?vs_currency=usd")
    fun getCoinList(): Call<List<CoinsDto>>
}