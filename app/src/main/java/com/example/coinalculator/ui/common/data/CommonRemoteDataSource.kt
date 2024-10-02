package com.example.coinalculator.ui.common.data

import retrofit2.await

class CommonRemoteDataSource(
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

    suspend fun getRub(): RubDTO {
        return try {
            coinApi.getRub("usd", "rub").await()
        } catch (e: Exception) {
            println("Error while getting query result from server: $e")
            RubDTO(Rub(0f))
        }
    }
}