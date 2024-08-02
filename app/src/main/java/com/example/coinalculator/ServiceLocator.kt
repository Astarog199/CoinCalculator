package com.example.coinalculator

import android.content.Context
import androidx.room.Room
import com.example.coinalculator.ui.card.domain.CoinDetailsDomainMapper
import com.example.coinalculator.ui.card.domain.ConsumeCoinCard
import com.example.coinalculator.ui.common.data.CoinsDataMapper
import com.example.coinalculator.ui.common.data.CoinsLocalDataSource
import com.example.coinalculator.ui.common.data.CoinsRemoteDataSource
import com.example.coinalculator.ui.coins.data.CoinsRepositoryImpl
import com.example.coinalculator.ui.coins.data.CoinsMapper
import com.example.coinalculator.ui.common.data.CoinsApiService
import com.example.coinalculator.ui.common.data.room.CoinDao
import com.example.coinalculator.ui.common.data.room.CoinsDB
import com.example.coinalculator.ui.coins.domain.ConsumeCoinsUseCase
import com.example.coinalculator.ui.coins.domain.FilterCoinsListUseCase
import com.example.coinalculator.ui.common.data.CommonRepository
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

    private fun provideRepository(): CoinsRepositoryImpl {
        return CoinsRepositoryImpl(
            coinsMapper = provideCoinsMapper(),
            coinsRepository = provideCommonRepository()
        )
    }


    private fun provideCommonRepository(): CommonRepository {
        return CommonRepository(
            coinsRemoteDataSource = provideCoinsListRemoteDataSource(),
            coinsDataMapper = provideCoinDataMapper(),
            coinsLocalDataSource = provideDashboardLocalDataSource(),
            coroutineDispatcher = provideIOCoroutineDispatcher()
        )
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
        return ConsumeCoinsUseCase(coinsRepository = provideRepository())
    }

    fun provideFilterCoinsListUseCase(): FilterCoinsListUseCase {
        return FilterCoinsListUseCase(coinsRepository = provideRepository())
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

    fun provideConsumeCoinCard(): ConsumeCoinCard {
        return ConsumeCoinCard(
            coinsRepository = provideRepository(),
            coinDetailsDomainMapper = provideCoinDetailsDomainMapper()
        )
    }

    private fun provideCoinDetailsDomainMapper(): CoinDetailsDomainMapper {
        return CoinDetailsDomainMapper()
    }
}