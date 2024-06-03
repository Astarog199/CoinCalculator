package com.example.coinalculator.ui.home.data.API

import retrofit2.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

    val retrofit = Retrofit
        .Builder()
        .client(
            OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            }).build())
        .baseUrl("https://api.coingecko.com/api/v3/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(ListCoinApi::class.java)

interface ListCoinApi {
    @GET("simple/supported_vs_currencies")
    fun getList(): Call<List<String>>
}