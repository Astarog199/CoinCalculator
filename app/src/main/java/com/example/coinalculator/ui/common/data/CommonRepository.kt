package com.example.coinalculator.ui.common.data

import com.example.coinalculator.ui.calculator.domain.DomainEntity
import com.example.coinalculator.ui.coins.domain.CoinEntity
import com.example.coinalculator.ui.common.data.room.Coin
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class CommonRepository @Inject constructor(
    private val coinsRemoteDataSource: CommonRemoteDataSource,
    private val mapper: CommonDataMapper,
    private val coinsLocalDataSource: CommonLocalDataSource,
    private val coroutineDispatcher: CoroutineDispatcher,
) {
    private val scope = CoroutineScope(SupervisorJob() + coroutineDispatcher)
    private var newList: List<CoinsDto> = mutableListOf()
    private var _coins : List<Coin> = mutableListOf()


    private fun fillRepository() {
        scope.launch {
            coinsLocalDataSource.consume().collect { coins ->
                when {
                    coins.isEmpty() -> saveList()
                    else -> _coins = coins
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
                newList.map(mapper::toNewCoin)
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
                            pricePercentageChange24h = i.priceChangePercentage24h,
                            priceChange24h = i.priceChange24h,
                            marketCap = i.marketCap,
                            marketCapRank = i.marketCapRank,
                            totalVolume = i.totalVolume,
                            high24h = i.high24h,
                            low24h = i.low24h,
                            circulatingSupply = i.circulatingSupply,
                            totalSupply = i.totalSupply,
                            maxSupply = i.maxSupply,
                        )
                    )
                }
            }
        }
    }

    fun consumeCoins(): Flow<List<CoinEntity>> {
        scope.launch(Dispatchers.Default) {
            requestListFromApiService()
            fillRepository()
            updateValueForList(_coins)
        }

        return getList().map { it.map(mapper::toCoinEntity) }
    }

    suspend fun changeFavoriteState(coin: CoinEntity) {
        val value = mapper.toCoin(coin)
        coinsLocalDataSource.addFavorite(value)
    }

    fun consumeCalculatorCoins(): Flow<List<DomainEntity>> {
        return getList().map {
            it.filter { favorites ->
                favorites.isFavorite
            }.map(mapper::toCalculator)
        }
    }
}