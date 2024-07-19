package com.example.coinalculator.ui.dashboard.data

import com.example.coinalculator.ui.dashboard.domain.Coin
import com.example.coinalculator.ui.dashboard.domain.DashboardRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import retrofit2.await
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DashboardRepositoryImpl(
    private val coinApi: SearchApi,
    private val coinDataMapper: CoinDataMapper,
    private val dashboardLocalDataSource: DashboardLocalDataSource

) : DashboardRepository {
    private val scope = CoroutineScope(Dispatchers.IO)
    val coinDao = dashboardLocalDataSource.db.coinDao()


    private suspend fun getList(): List<CoinDto> {
        return coinApi.getCoinList().await()
    }

    private suspend fun saveList() {
        scope.launch {
            getList().map { coinDto ->
                coinDao.insert(
                    coinEntity = CoinEntity(
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

        return coinDao.getALL().map { coins ->
            coins.map(coinDataMapper::fromEntity)
        }

    }
}