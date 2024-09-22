package com.example.coinalculator.ui.common.data

import com.example.coinalculator.ui.coins.domain.CoinEntity
import com.example.coinalculator.ui.coins.domain.CoinsRepository
import com.example.coinalculator.ui.common.data.room.Coin
import com.example.coinalculator.ui.common.data.room.NewCoin
import com.example.coinalculator.ui.favorite.domain.FavoriteEntity
import com.example.coinalculator.ui.favorite.domain.FavoriteRepository
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
) : CoinsRepository, FavoriteRepository {
    private val scope = CoroutineScope(SupervisorJob() + coroutineDispatcher)
    private var job: Job? = null
    private var newList: List<CoinsDto> = mutableListOf()
    private lateinit var refreshTimer: Job

    init {
        refresh()
    }

    private fun refresh() {
        refreshTimer = scope.launch(Dispatchers.Default) {
            while (true) {
                requestListFromApiService()
                fillRepository()
                delay(60000L)// 60 seconds
            }
        }
    }

    private fun fillRepository() {
        job = scope.launch {
            if (newList.isEmpty()) {
                requestListFromApiService()
            }
            coinsLocalDataSource.consume().collect { coins ->
                when {
                    coins.isEmpty() -> saveList()
                    else -> updateValueForList(coins)
                }
            }
        }
    }

    private suspend fun requestListFromApiService() {
        newList = coinsRemoteDataSource.getList()
    }

    private fun saveList() {
        scope.launch {
            coinsLocalDataSource.saveMany(
                newList.map { coin ->
                    NewCoin(
                        name = coin.name,
                        image = coin.image,
                        price = coin.currentPrice,
                        price_percentage_change_24h = coin.priceChangePercentage24h,
                        priceChange24h = coin.priceChange24h,
                        marketCap = coin.marketCap,
                        marketCapRank = coin.marketCapRank,
                        totalVolume = coin.totalVolume
                    )
                }
            )
        }
    }

    private fun getList(): Flow<List<Coin>> {
        return coinsLocalDataSource.consume().flowOn(coroutineDispatcher)
    }

    private suspend fun updateValueForList(list: List<Coin>) {
        list.map { coin ->
            for (i in newList) {
                if (i.name == coin.name) {
                    coinsLocalDataSource.updateCoin(
                        coin.copy(
                            price = i.currentPrice,
                            price_percentage_change_24h = i.priceChangePercentage24h,
                            price_change_24h = i.priceChange24h,
                            market_cap = i.marketCap,
                            market_cap_rank = i.marketCapRank,
                            total_volume = i.totalVolume,
                        )
                    )
                }
            }
        }
    }

    override fun consumeCoins(): Flow<List<CoinEntity>> {
        return getList()
            .map { coins ->
                coins.map(coinsDataMapper::toCoinEntity)
            }
    }

    override fun filterCoins(query: String): Flow<List<CoinEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun changeFavoriteState(coin: CoinEntity) {
        val value = coinsDataMapper.toCoin(coin)
        job?.cancel()
        coinsLocalDataSource.addFavorite(value)

    }

    override fun consumeFavoriteCoins(): Flow<List<FavoriteEntity>> {
        return getList()
            .map { coins ->
                coins.filter { favorites ->
                    favorites.isFavorite
                }
                    .map(coinsDataMapper::toFavorite)
            }
    }
}