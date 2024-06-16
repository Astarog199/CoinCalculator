package com.example.coinalculator.ui.dashboard

import androidx.lifecycle.ViewModelProvider
import com.example.coinalculator.ui.dashboard.data.DashboardRepositoryImpl
import com.example.coinalculator.ui.dashboard.data.SearchApi
import com.example.coinalculator.ui.dashboard.domain.ConsumeDashboardUseCase
import com.example.coinalculator.ui.dashboard.domain.FilterCoinsListUseCase
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ServiceLocator {
    fun provideViewModel() : ViewModelProvider.Factory{
        return ViewModelFactory(
            consumeDashboardUseCase = provideConsumeDashboardUseCase(),
            filterCoinsListUseCase =  provideFilterCoinsListUseCase()
        )
    }

    private fun provideRetrofit(): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/api/v3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        return retrofit
    }

    private fun  provideDataApiService(): SearchApi {
        return provideRetrofit().create(SearchApi::class.java)
    }

    private fun provideDataRemoteDataSource(): DashboardRepositoryImpl{
        return DashboardRepositoryImpl(coinApi= provideDataApiService())
    }

    private fun provideConsumeDashboardUseCase(): ConsumeDashboardUseCase {
        return ConsumeDashboardUseCase(repositoryImpl = provideDataRemoteDataSource())
    }

    private fun provideFilterCoinsListUseCase(): FilterCoinsListUseCase{
        return FilterCoinsListUseCase(repositoryImpl = provideDataRemoteDataSource())
    }
}