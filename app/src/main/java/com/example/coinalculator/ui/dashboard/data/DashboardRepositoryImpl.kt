package com.example.coinalculator.ui.dashboard.data

import com.example.coinalculator.ui.dashboard.domain.Coin
import com.example.coinalculator.ui.dashboard.domain.DashboardRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import retrofit2.await
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DashboardRepositoryImpl(
    private val coinApi: SearchApi,
    private val coinDataMapper: CoinDataMapper,
    private val dashboardLocalDataSource: DashboardLocalDataSource,
    private val coroutineDispatcher: CoroutineDispatcher
) : DashboardRepository {
    private val scope = CoroutineScope(Dispatchers.IO)


    private suspend fun getList(): List<CoinDto> {
        return coinApi.getCoinList().await()
    }

    private suspend fun saveList() {
        scope.launch {
            getList().map { coinDto ->
                dashboardLocalDataSource.saveProducts(
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


        val value = dashboardLocalDataSource.consume().stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

        return value.map { coins ->
            coins.map(coinDataMapper::fromEntity)
        }
    }
}