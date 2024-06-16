package com.example.coinalculator.ui.home

import androidx.lifecycle.ViewModelProvider
import com.example.coinalculator.ui.home.data.CoinsListRepository
import com.example.coinalculator.ui.home.data.ListCoinApi
import com.example.coinalculator.ui.home.presently.ViewModelFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


import retrofit2.converter.moshi.MoshiConverterFactory


object ServiceLocator {
    
    fun provideViewModel() : ViewModelProvider.Factory{
        return ViewModelFactory(
            provideDataRemoteDataSource()
        )
    }
    
    private fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(
                OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
                    it.level = HttpLoggingInterceptor.Level.BODY
                }).build())
            .baseUrl("https://api.coingecko.com/api/v3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private fun provideDataApiService(): ListCoinApi {
        return provideRetrofit().create(ListCoinApi::class.java)
    }
    
    private fun provideDataRemoteDataSource(): CoinsListRepository{
        return CoinsListRepository(listCoinApi = provideDataApiService())
    }
    
}


