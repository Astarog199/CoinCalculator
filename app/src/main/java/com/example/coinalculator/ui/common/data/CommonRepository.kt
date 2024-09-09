package com.example.coinalculator.ui.common.data

import com.example.coinalculator.ui.common.data.room.Entity
import com.example.coinalculator.ui.common.data.room.NewCoin
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class CommonRepository(
    private val coinsRemoteDataSource: CommonRemoteDataSource,
    private val coinsDataMapper: CommonDataMapper,
    private val coinsLocalDataSource: CommonLocalDataSource,
    private val coroutineDispatcher: CoroutineDispatcher
) {
    private val scope = CoroutineScope(SupervisorJob() + coroutineDispatcher)

    private fun saveList() {
        scope.launch {
            val coins = coinsRemoteDataSource.getList()
                .map(coinsDataMapper::toEntity)

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
                        isFavorite = coin.isFavorite
                    )
                }
            )
        }
    }

    fun getList(): Flow<List<Entity>> {
        scope.launch {
            coinsLocalDataSource.consume().collect { coins ->
                if (coins.isEmpty()){
                    saveList()
                }else{
                    coins.map { coin ->
                        for (i in coinsRemoteDataSource.getList()) {
                            if (i.name == coin.name) {
                                coinsLocalDataSource.updateCoin(
                                    Entity(
                                        name = coin.name,
                                        image = i.image,
                                        price = i.currentPrice.toString(),
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
            }
        }
        return coinsLocalDataSource.consume().flowOn(coroutineDispatcher)
    }

    suspend fun changeFavorite(coin: Entity) {
        coinsLocalDataSource.addFavorite(coin)
    }
}