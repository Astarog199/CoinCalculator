package com.example.coinalculator

import android.content.Context
import com.example.coinalculator.ui.calculator.domain.ConsumeCalculatorUseCase
import com.example.coinalculator.ui.common.data.CommonDataMapper
import com.example.coinalculator.ui.common.data.CommonLocalDataSource
import com.example.coinalculator.ui.common.data.CommonRemoteDataSource
import com.example.coinalculator.ui.coins.domain.ChangeFavoriteStateUseCase
import com.example.coinalculator.ui.coins.domain.ConsumeCoinCardUseCase
import com.example.coinalculator.ui.common.data.CommonApiService
import com.example.coinalculator.ui.common.data.room.CoinDao
import com.example.coinalculator.ui.coins.domain.ConsumeCoinListUseCase
import com.example.coinalculator.ui.coins.domain.FilterCoinsListUseCase
import com.example.coinalculator.ui.common.data.CommonRepositoryImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ServiceLocator {
    private var retrofitSingleton: Retrofit? = null
    private var commonRepositorySingleton: CommonRepositoryImpl? = null
    lateinit var applicationContext: Context

    private fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    private fun provideRetrofit(): Retrofit {
        val local = retrofitSingleton

        return local ?: run {
            val newRetrofit = Retrofit.Builder()
                .client(provideOkHttpClient())
                .baseUrl("https://api.coingecko.com/api/v3/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            retrofitSingleton = newRetrofit
            newRetrofit
        }
    }

    private fun provideDataApiService(): CommonApiService {
        return provideRetrofit().create(CommonApiService::class.java)
    }

    private fun provideCommonRepository(): CommonRepositoryImpl {
        val local = commonRepositorySingleton
        return local ?: run {
            val newCommonRepository = CommonRepositoryImpl(
                coinsRemoteDataSource = provideCoinsListRemoteDataSource(),
                coinsDataMapper = provideCoinDataMapper(),
                coinsLocalDataSource = provideDashboardLocalDataSource(),
                coroutineDispatcher = provideIOCoroutineDispatcher(),
            )
            commonRepositorySingleton = newCommonRepository
            newCommonRepository
        }

    }

    private fun provideIOCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

     fun provideCoinsListRemoteDataSource(): CommonRemoteDataSource {
        return CommonRemoteDataSource(coinApi = provideDataApiService())
    }

    private fun provideCoinDataMapper(): CommonDataMapper {
        return CommonDataMapper()
    }

    fun provideConsumeDashboardUseCase(): ConsumeCoinListUseCase {
        return ConsumeCoinListUseCase(coinsRepository = provideCommonRepository())
    }

    fun provideFilterCoinsListUseCase(): FilterCoinsListUseCase {
        return FilterCoinsListUseCase(coinsRepository = provideCommonRepository())
    }

    fun provideConsumeCalculatorUseCase () : ConsumeCalculatorUseCase {
        return ConsumeCalculatorUseCase(provideCommonRepository())
    }

    private fun provideDashboardLocalDataSource(): CommonLocalDataSource {
        return CommonLocalDataSource(coinDao = provideRoom())
    }

    private fun provideRoom(): CoinDao {
        return (applicationContext as App).db.coinDao()
    }

    fun provideConsumeCoinCardUseCase(): ConsumeCoinCardUseCase {
        return ConsumeCoinCardUseCase(
            coinsRepository = provideCommonRepository(),
        )
    }

    fun provideAddFavoriteUseCase(): ChangeFavoriteStateUseCase {
        return ChangeFavoriteStateUseCase(
            coinsRepository = provideCommonRepository()
        )
    }
}