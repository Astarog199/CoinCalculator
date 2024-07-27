package com.example.coinalculator.ui.Coins

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.coinalculator.ui.Coins.data.CoinsDataMapper
import com.example.coinalculator.ui.Coins.data.CoinsLocalDataSource
import com.example.coinalculator.ui.Coins.data.CoinsRemoteDataSource
import com.example.coinalculator.ui.Coins.data.CoinsRepositoryImpl
import com.example.coinalculator.ui.Coins.data.CoinsMapper
import com.example.coinalculator.ui.Coins.data.CoinsApiService
import com.example.coinalculator.ui.Coins.data.room.CoinDao
import com.example.coinalculator.ui.Coins.data.room.CoinsDB
import com.example.coinalculator.ui.Coins.domain.ConsumeCoinsUseCase
import com.example.coinalculator.ui.Coins.domain.FilterCoinsListUseCase
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