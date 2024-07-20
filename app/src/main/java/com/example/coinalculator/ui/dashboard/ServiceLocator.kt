package com.example.coinalculator.ui.dashboard

import androidx.lifecycle.ViewModelProvider
import com.example.coinalculator.ui.dashboard.data.CoinDataMapper
import com.example.coinalculator.ui.dashboard.data.DashboardLocalDataSource
import com.example.coinalculator.ui.dashboard.data.DashboardRepositoryImpl
import com.example.coinalculator.ui.dashboard.data.SearchApi
import com.example.coinalculator.ui.dashboard.data.room.App
import com.example.coinalculator.ui.dashboard.data.room.CoinDao
import com.example.coinalculator.ui.dashboard.domain.ConsumeDashboardUseCase
import com.example.coinalculator.ui.dashboard.domain.FilterCoinsListUseCase
import com.example.coinalculator.ui.dashboard.presently.CoinVOMapper
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ServiceLocator {
    private var dashboardRepositorySingleton: DashboardRepositoryImpl? = null

    fun provideViewModel(): ViewModelProvider.Factory {
        return ViewModelFactory(
            consumeDashboardUseCase = provideConsumeDashboardUseCase(),
            filterCoinsListUseCase = provideFilterCoinsListUseCase(),
            coinVOMapper = CoinVOMapper()
        )
    }

    private fun provideRetrofit(): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/api/v3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        return retrofit
    }

    private fun provideDataApiService(): SearchApi {
        return provideRetrofit().create(SearchApi::class.java)
    }

    private fun providDashboardRepositoryImpl(): DashboardRepositoryImpl {
        val local = dashboardRepositorySingleton

        return local ?: run {
            val newRepositoryImpl = DashboardRepositoryImpl(
                coinApi = provideDataApiService(),
                coinDataMapper = provideCoinDataMapper(),
                dashboardLocalDataSource = provideDashboardLocalDataSource(),
                coroutineDispatcher = Dispatchers.IO
            )

            dashboardRepositorySingleton = newRepositoryImpl
            newRepositoryImpl
        }
    }

    private fun provideCoinDataMapper(): CoinDataMapper {
        return CoinDataMapper()
    }

    private fun provideConsumeDashboardUseCase(): ConsumeDashboardUseCase {
        return ConsumeDashboardUseCase(repositoryImpl = providDashboardRepositoryImpl())
    }

    private fun provideFilterCoinsListUseCase(): FilterCoinsListUseCase {
        return FilterCoinsListUseCase(repositoryImpl = providDashboardRepositoryImpl())
    }

    private fun provideDashboardLocalDataSource(): DashboardLocalDataSource {
        return DashboardLocalDataSource(coinDao = provideCoinDao())
    }

    private fun provideCoinDao(): CoinDao {
        return  App().db.coinDao()
    }
}