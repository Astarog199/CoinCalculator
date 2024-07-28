package com.example.coinalculator.ui.coins

import android.content.Context
import androidx.room.Room
import com.example.coinalculator.ui.coins.data.CoinsDataMapper
import com.example.coinalculator.ui.coins.data.CoinsLocalDataSource
import com.example.coinalculator.ui.common.data.CoinsRemoteDataSource
import com.example.coinalculator.ui.coins.data.CoinsRepositoryImpl
import com.example.coinalculator.ui.coins.data.CoinsMapper
import com.example.coinalculator.ui.common.data.CoinsApiService
import com.example.coinalculator.ui.common.data.room.CoinDao
import com.example.coinalculator.ui.common.data.room.CoinsDB
import com.example.coinalculator.ui.coins.domain.ConsumeCoinsUseCase
import com.example.coinalculator.ui.coins.domain.FilterCoinsListUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ServiceLocator {
    private var retrofitSingleton: Retrofit? = null
    private var coinsListRepositorySingleton: CoinsRepositoryImpl? = null

    lateinit var applicationContext: Context

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

    private fun provideDataApiService(): CoinsApiService {
        return provideRetrofit().create(CoinsApiService::class.java)
    }

    private fun providDashboardRepository(): CoinsRepositoryImpl {
        val local = coinsListRepositorySingleton
        return local ?: run {
            val newRepository = CoinsRepositoryImpl(
                coinsRemoteDataSource = provideCoinsListRemoteDataSource(),
                coinsDataMapper = provideCoinDataMapper(),
                coinsLocalDataSource = provideDashboardLocalDataSource(),
                coinsMapper = provideCoinsMapper(),
                coroutineDispatcher = provideIOCoroutineDispatcher()
            )

            coinsListRepositorySingleton = newRepository
            newRepository
        }
    }

    private fun provideIOCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    private fun provideCoinsListRemoteDataSource(): CoinsRemoteDataSource {
        return CoinsRemoteDataSource(coinApi = provideDataApiService())
    }

    private fun provideCoinDataMapper(): CoinsDataMapper {
        return CoinsDataMapper()
    }

    fun provideConsumeDashboardUseCase(): ConsumeCoinsUseCase {
        return ConsumeCoinsUseCase(coinsRepository = providDashboardRepository())
    }

    fun provideFilterCoinsListUseCase(): FilterCoinsListUseCase {
        return FilterCoinsListUseCase(coinsRepository = providDashboardRepository())
    }

    private fun provideDashboardLocalDataSource(): CoinsLocalDataSource {
        return CoinsLocalDataSource(coinDao = provideRoom())
    }

    private fun provideRoom(): CoinDao {
        val db = Room.inMemoryDatabaseBuilder(
            context = applicationContext,
            CoinsDB::class.java,
        )
            .fallbackToDestructiveMigration()
            .build()

        return db.coinDao()
    }



private fun provideCoinsMapper(): CoinsMapper {
    return CoinsMapper()
}
}