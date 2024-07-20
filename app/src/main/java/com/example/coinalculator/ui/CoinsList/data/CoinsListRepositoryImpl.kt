package com.example.coinalculator.ui.CoinsList.data

import com.example.coinalculator.ui.CoinsList.domain.Coin
import com.example.coinalculator.ui.CoinsList.domain.DashboardRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.await

class CoinsListRepositoryImpl(
    private val coinApi: SearchApi,
    private val coinsListDataMapper: CoinsListDataMapper,
    private val coinsListLocalDataSource: CoinsListLocalDataSource,
    private val coroutineDispatcher: CoroutineDispatcher
) : DashboardRepository {
    private val scope = CoroutineScope(Dispatchers.IO)


    private suspend fun getList(): List<CoinsListDto> {
        return coinApi.getCoinList().await()
    }

    private suspend fun saveList() {
        scope.launch {
            getList().map { coinDto ->
                coinsListLocalDataSource.saveProducts(
                    coinsListEntity = CoinsListEntity(
                        coinDto.index_id.toString(),
                        coinDto.market.toString(),
                        coinDto.price
                    )
                )
            }
        }
    }

    override suspend fun consumeCoins(): Flow<List<Coin>> {
        saveList()


        val value = coinsListLocalDataSource.consume().stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

        return value.map { coins ->
            coins.map(coinsListDataMapper::fromEntity)
        }
    }
}