package com.example.coinalculator.ui.CoinsList

import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.coinalculator.ui.CoinsList.data.CoinsListDataMapper
import com.example.coinalculator.ui.CoinsList.data.CoinsListLocalDataSource
import com.example.coinalculator.ui.CoinsList.data.CoinsListRepositoryImpl
import com.example.coinalculator.ui.CoinsList.data.SearchApi
import com.example.coinalculator.ui.CoinsList.data.room.CoinsDB
import com.example.coinalculator.ui.CoinsList.domain.ConsumeDashboardUseCase
import com.example.coinalculator.ui.CoinsList.domain.FilterCoinsListUseCase
import com.example.coinalculator.ui.CoinsList.presently.CoinVOMapper
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ServiceLocator {
    private var retrofitSingleton: Retrofit? = null

    private val appContext by  lazy {
        App.getInstance()
    }

    fun provideViewModel(): ViewModelProvider.Factory {
        return ViewModelFactory(
            consumeDashboardUseCase = provideConsumeDashboardUseCase(),
            filterCoinsListUseCase = provideFilterCoinsListUseCase(),
            coinVOMapper = CoinVOMapper()
        )
    }

    private fun provideRetrofit(): Retrofit {
        val local = retrofitSingleton

        return local ?: run {
            val newRetrofit = Retrofit.Builder()
                .baseUrl("https://api.coingecko.com/api/v3/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            retrofitSingleton = newRetrofit
            newRetrofit
        }
    }

    private fun provideDataApiService(): SearchApi {
        return provideRetrofit().create(SearchApi::class.java)
    }

    private fun providDashboardRepositoryImpl(): CoinsListRepositoryImpl {
        return  CoinsListRepositoryImpl(
                coinApi = provideDataApiService(),
                coinsListDataMapper = provideCoinDataMapper(),
                coinsListLocalDataSource = provideDashboardLocalDataSource(),
                coroutineDispatcher = Dispatchers.IO
            )
        }


    private fun provideCoinDataMapper(): CoinsListDataMapper {
        return CoinsListDataMapper()
    }

    private fun provideConsumeDashboardUseCase(): ConsumeDashboardUseCase {
        return ConsumeDashboardUseCase(repositoryImpl = providDashboardRepositoryImpl())
    }

    private fun provideFilterCoinsListUseCase(): FilterCoinsListUseCase {
        return FilterCoinsListUseCase(repositoryImpl = providDashboardRepositoryImpl())
    }

    private fun provideDashboardLocalDataSource(): CoinsListLocalDataSource {
        val db = Room.inMemoryDatabaseBuilder(
            appContext,
            CoinsDB::class.java,
        )
            .fallbackToDestructiveMigration()
            .build()

        return CoinsListLocalDataSource(coinDao = db.coinDao())
    }
}