package com.example.coinalculator.ui.common.data

import com.example.coinalculator.ui.calculator.domain.CalculatorRepository
import com.example.coinalculator.ui.calculator.domain.DomainEntity
import com.example.coinalculator.ui.coins.domain.CoinEntity
import com.example.coinalculator.ui.coins.domain.CoinsRepository
import com.example.coinalculator.ui.common.data.room.Coin
import com.example.coinalculator.ui.common.data.room.NewCoin
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
    private val coroutineDispatcher: CoroutineDispatcher,
) : CalculatorRepository, CoinsRepository {
    private val scope = CoroutineScope(SupervisorJob() + coroutineDispatcher)
    private var newList: List<CoinsDto> = mutableListOf()
    private var rub = DomainEntity(symbol = "rub", price = 0f)
    private var _coins : List<Coin> = mutableListOf()

    init {
        scope.launch(Dispatchers.Default) {
            rub = getRUB()
            requestListFromApiService()
            fillRepository()
            updateValueForList(_coins)
        }
    }

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

    private suspend fun getRUB(): DomainEntity {
        return coinsDataMapper.rubToEntity(coinsRemoteDataSource.getRub(), "rub")
    }

    private fun saveList() {
        scope.launch {
            coinsLocalDataSource.saveMany(
                newList.map { coin ->
                    NewCoin(
                        symbol = coin.symbol,
                        name = coin.name,
                        image = coin.image,
                        price = coin.currentPrice,
                        pricePercentageChange24h = coin.priceChangePercentage24h,
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
                            pricePercentageChange24h = i.priceChangePercentage24h,
                            priceChange24h = i.priceChange24h,
                            marketCap = i.marketCap,
                            marketCapRank = i.marketCapRank,
                            totalVolume = i.totalVolume,
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
        coinsLocalDataSource.addFavorite(value)
    }

    override fun consumeCalculatorCoins(): Flow<List<DomainEntity>> {
        return getList().map {
            it.filter { favorites ->
                favorites.isFavorite
            }.map(coinsDataMapper::toCalculator) + DomainEntity(symbol = "usd", price = 1f) + rub
        }
    }
}