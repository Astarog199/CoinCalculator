package com.example.coinalculator.ui.common.data

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import retrofit2.await
import javax.inject.Inject

class CommonRemoteDataSource @Inject constructor(
    private val coinApi: CommonApiService,
) {
     suspend fun getList(): List<CoinsDto> {
         return try {
                coinApi.getCoinList().await()
         } catch (e: Exception) {
             FirebaseCrashlytics.getInstance().recordException(e)
             Log.d("ERROR", "CommonRemoteDataSource: $e")
             emptyList()
         }
     }
}