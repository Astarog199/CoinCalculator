package com.example.coinalculator.ui.common.data

import com.example.coinalculator.ui.common.data.room.NewCoin
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class CommonRepository(
    private val coinsRemoteDataSource: CoinsRemoteDataSource,
    private val coinsDataMapper: CoinsDataMapper,
    private val coinsLocalDataSource: CoinsLocalDataSource,
    private val coroutineDispatcher: CoroutineDispatcher
) {
    private val scope = CoroutineScope(SupervisorJob() + coroutineDispatcher)

     fun saveList(): Flow<List<CoinsEntity>> {
        scope.launch {
            val coins = coinsRemoteDataSource.getList()
                .map(coinsDataMapper::toEntity)

            coins.map { coin ->
                coinsLocalDataSource.save(
                    NewCoin(
                        name = coin.name,
                        market = coin.market,
                        price = coin.price,
                        price_percentage_change_24h = coin.price_percentage_change_24h,
                        isFavorite = coin.isFavorite
                    )
                )
            }
        }

         return coinsLocalDataSource.consume().flowOn(coroutineDispatcher)
    }
}