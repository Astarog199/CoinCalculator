package com.example.coinalculator.ui.coins.data

import retrofit2.Call
import retrofit2.http.GET

interface CoinsApiService {
    @GET("derivatives")
    fun getCoinList(): Call<List<CoinsDto>>
}