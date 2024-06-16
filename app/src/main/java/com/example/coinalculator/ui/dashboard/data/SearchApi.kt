package com.example.coinalculator.ui.dashboard.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("simple/price")
    fun getCoinApi(@Query("ids") ids: String, @Query("vs_currencies") vsCurrencies: String): Call<CoinModel>

    @GET("derivatives")
    fun getCoinList(): Call<List<ElementList>>
}