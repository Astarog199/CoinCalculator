package com.example.coinalculator.ui.dashboard.data

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val BASE_URL =
    "https://api.coingecko.com/api/v3/"

object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val searchCoinApi: SearchApi = retrofit.create(SearchApi::class.java)
}

interface SearchApi {
    @GET("simple/price")
    fun getCoinApi(@Query("ids") ids: String, @Query("vs_currencies") vsCurrencies: String): Call<CoinModel>
}