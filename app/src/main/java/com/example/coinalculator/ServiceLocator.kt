package com.example.coinalculator

import android.content.Context
import androidx.room.Room
import com.example.coinalculator.ui.common.data.CommonDataMapper
import com.example.coinalculator.ui.common.data.CommonLocalDataSource
import com.example.coinalculator.ui.common.data.CommonRemoteDataSource
import com.example.coinalculator.ui.coins.data.CoinsRepositoryImpl
import com.example.coinalculator.ui.coins.data.Mapper
import com.example.coinalculator.ui.coins.domain.ChangeFavoriteStateUseCase
import com.example.coinalculator.ui.coins.domain.ConsumeCoinCardUseCase
import com.example.coinalculator.ui.common.data.CommonApiService
import com.example.coinalculator.ui.common.data.room.CoinDao
import com.example.coinalculator.ui.common.data.room.CoinsDB
import com.example.coinalculator.ui.coins.domain.ConsumeCoinListUseCase
import com.example.coinalculator.ui.coins.domain.FilterCoinsListUseCase
import com.example.coinalculator.ui.common.data.CommonRepository
import com.example.coinalculator.ui.favorite.data.FavoriteMapper
import com.example.coinalculator.ui.favorite.data.FavoritesRepositoryImpl
import com.example.coinalculator.ui.favorite.domain.ConsumeFavorietesUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ServiceLocator {
    private var retrofitSingleton: Retrofit? = null
    private var commonRepositorySingleton: CommonRepository? = null

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

    private fun provideRepository(): CoinsRepositoryImpl {
        return CoinsRepositoryImpl(
            coinsMapper = provideCoinsMapper(),
            coinsRepository = provideCommonRepository()
        )
    }


    private fun provideCommonRepository(): CommonRepository {
        val local = commonRepositorySingleton
        return local ?: run {
            val newCommonRepository = CommonRepository(
                coinsRemoteDataSource = provideCoinsListRemoteDataSource(),
                coinsDataMapper = provideCoinDataMapper(),
                coinsLocalDataSource = provideDashboardLocalDataSource(),
                coroutineDispatcher = provideIOCoroutineDispatcher()
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
        return ConsumeCoinListUseCase(coinsRepository = provideRepository())
    }

    fun provideFilterCoinsListUseCase(): FilterCoinsListUseCase {
        return FilterCoinsListUseCase()
    }

    private fun provideDashboardLocalDataSource(): CommonLocalDataSource {
        return CommonLocalDataSource(coinDao = provideRoom())
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

    private fun provideCoinsMapper(): Mapper {
        return Mapper()
    }

    fun provideConsumeCoinCardUseCase(): ConsumeCoinCardUseCase {
        return ConsumeCoinCardUseCase(
            coinsRepository = provideRepository(),
        )
    }

    fun provideAddFavoriteUseCase(): ChangeFavoriteStateUseCase {
        return ChangeFavoriteStateUseCase(
            coinsRepository = provideRepository()
        )
    }

    private fun provideFavoritesRepository() : FavoritesRepositoryImpl {
        return FavoritesRepositoryImpl(
            coinsRepository = provideCommonRepository(),
            favoriteMapper = FavoriteMapper()
            )
    }

    fun provideConsumeFavoriteList(): ConsumeFavorietesUseCase {
        return ConsumeFavorietesUseCase(favoriteRepository = provideFavoritesRepository())
    }
}