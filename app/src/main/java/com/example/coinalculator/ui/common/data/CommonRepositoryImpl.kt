package com.example.coinalculator.ui.common.data

import com.example.coinalculator.ui.common.data.room.Entity
import com.example.coinalculator.ui.common.data.room.NewCoin
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CommonRepositoryImpl(
    private val coinsRemoteDataSource: CommonRemoteDataSource,
    private val coinsDataMapper: CommonDataMapper,
    private val coinsLocalDataSource: CommonLocalDataSource,
    private val coroutineDispatcher: CoroutineDispatcher
) {
    private val scope = CoroutineScope(SupervisorJob() + coroutineDispatcher)
    private var newList: List<CoinsDto> = mutableListOf()
    private lateinit var refreshTimer: Job

    init {
        refresh()
    }

    private fun refresh() {
        refreshTimer = scope.launch(Dispatchers.Default) {
            while (true) {
                requestListFromApiService()
                delay(60000L)// 60 seconds
            }
        }
    }

    private suspend fun requestListFromApiService() {
        newList = coinsRemoteDataSource.getList()
    }

    private fun saveList() {
        scope.launch {
            val coins = newList.map(coinsDataMapper::toEntity)
            coinsLocalDataSource.saveMany(
                coins.map { coin ->
                    NewCoin(
                        name = coin.name,
                        image = coin.image,
                        price = coin.price,
                        price_percentage_change_24h = coin.pricePercentageChange24h,
                        priceChange24h = coin.priceChange24h,
                        marketCap = coin.marketCap,
                        marketCapRank = coin.marketCapRank,
                        totalVolume = coin.totalVolume,
                    )
                }
            )
        }
    }

    fun getList(): Flow<List<Entity>> {
        scope.launch {
            if (newList.isEmpty()) {
                requestListFromApiService()
            }
            coinsLocalDataSource.consume().collect { coins ->
                if (coins.isEmpty()) {
                    saveList()
                } else {
                    updateValueForList(coins)
                }
            }
        }
        return coinsLocalDataSource.consume().flowOn(coroutineDispatcher)
    }

    private suspend fun updateValueForList(list: List<Entity>) {
        list.map { coin ->
            for (i in newList) {
                if (i.name == coin.name) {
                    coinsLocalDataSource.updateCoin(
                        Entity(
                            name = coin.name,
                            image = i.image,
                            price = i.currentPrice,
                            price_percentage_change_24h = i.priceChangePercentage24h,
                            price_change_24h = i.priceChange24h,
                            market_cap = i.marketCap,
                            market_cap_rank = i.marketCapRank,
                            total_volume = i.totalVolume,
                            isFavorite = coin.isFavorite
                        )
                    )
                }
            }
        }
    }

    suspend fun changeFavorite(coin: Entity) {
        coinsLocalDataSource.addFavorite(coin)
    }
}