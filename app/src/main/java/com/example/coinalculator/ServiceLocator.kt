package com.example.coinalculator

import android.content.Context
import androidx.room.Room
import com.example.coinalculator.ui.coins.domain.CoinsMapper
import com.example.coinalculator.ui.coins.domain.ConsumeCoinCard
import com.example.coinalculator.ui.common.data.CoinsDataMapper
import com.example.coinalculator.ui.common.data.CoinsLocalDataSource
import com.example.coinalculator.ui.common.data.CoinsRemoteDataSource
import com.example.coinalculator.ui.coins.data.CoinsRepositoryImpl
import com.example.coinalculator.ui.coins.data.Mapper
import com.example.coinalculator.ui.coins.domain.AddFavoriteUseCase
import com.example.coinalculator.ui.common.data.CoinsApiService
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
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ServiceLocator {
    private var retrofitSingleton: Retrofit? = null
    private var commonRepositorySingleton: CommonRepository? = null

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

    private fun provideCoinsListRemoteDataSource(): CoinsRemoteDataSource {
        return CoinsRemoteDataSource(coinApi = provideDataApiService())
    }

    private fun provideCoinDataMapper(): CoinsDataMapper {
        return CoinsDataMapper()
    }

    fun provideConsumeDashboardUseCase(): ConsumeCoinListUseCase {
        return ConsumeCoinListUseCase(coinsRepository = provideRepository())
    }

    fun provideFilterCoinsListUseCase(): FilterCoinsListUseCase {
        return FilterCoinsListUseCase()
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

    private fun provideCoinsMapper(): Mapper {
        return Mapper()
    }

    fun provideConsumeCoinCard(): ConsumeCoinCard {
        return ConsumeCoinCard(
            coinsRepository = provideRepository(),
            cardMapper = provideCardMapper()
        )
    }

    private fun provideCardMapper(): CoinsMapper {
        return CoinsMapper()
    }

    fun provideAddFavoriteUseCase(): AddFavoriteUseCase {
        return AddFavoriteUseCase(
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