package com.example.coinalculator.ui.home.data

import com.example.coinalculator.ui.home.data.API.retrofit
import retrofit2.await



class CoinsListRepository {
    suspend fun getCoins(): List<String> {
        return retrofit.getList().await()
    }
}