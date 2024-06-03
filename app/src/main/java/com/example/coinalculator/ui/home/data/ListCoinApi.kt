package com.example.coinalculator.ui.home.data

import retrofit2.Call
import retrofit2.http.GET


    interface ListCoinApi {
        @GET("simple/supported_vs_currencies")
        fun getList(): Call<List<String>>
    }
