package com.example.coinalculator.ui.CoinsList.data

import retrofit2.Call
import retrofit2.http.GET

interface SearchApi {
    @GET("derivatives")
    fun getCoinList(): Call<List<CoinsListDto>>
}