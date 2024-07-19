package com.example.coinalculator.ui.dashboard.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("derivatives")
    fun getCoinList(): Call<List<CoinDto>>
}