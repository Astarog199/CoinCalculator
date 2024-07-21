package com.example.coinalculator.ui.Coins.data

import retrofit2.Call
import retrofit2.http.GET

interface CoinsApiService {
    @GET("derivatives")
    fun getCoinList(): Call<List<CoinsDto>>
}