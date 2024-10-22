package com.example.coinalculator.ui.common.data

import retrofit2.await
import javax.inject.Inject

class CommonRemoteDataSource @Inject constructor(
    private val coinApi: CommonApiService,
) {
     suspend fun getList(): List<CoinsDto> {
         return try {
             coinApi.getCoinList().await()
         } catch (e: Exception) {
             println("Error while getting query result from server: $e")
             emptyList()
         }
     }
}