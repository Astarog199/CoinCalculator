package com.example.coinalculator.ui.Coins.data

import com.example.coinalculator.ui.Coins.data.room.NewCoin
import com.example.coinalculator.ui.Coins.domain.Coin
import com.example.coinalculator.ui.Coins.domain.CoinsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CoinsRepositoryImpl(
    private val coinsRemoteDataSource: CoinsRemoteDataSource,
    private val coinsDataMapper: CoinsDataMapper,
    private val coinsLocalDataSource: CoinsLocalDataSource,
    private val coinsMapper: CoinsMapper,
    private val coroutineDispatcher: CoroutineDispatcher
) : CoinsRepository {
    private val scope = CoroutineScope(SupervisorJob() + coroutineDispatcher)

    override fun consumeCoins(): Flow<List<Coin>> {
        return saveList()
            .map { coins ->
                coins.map(coinsMapper::fromEntity)
            }
    }

    private fun saveList(): Flow<List<CoinsEntity>> {
        scope.launch {
            val coins = coinsRemoteDataSource.getList()
                .map(coinsDataMapper::toEntity)


            coins.map { coin ->
                coinsLocalDataSource.save(
                    NewCoin(
                        name = coin.name,
                        market = coin.market,
                        price = coin.price
                    )
                )
            }
        }

        return coinsLocalDataSource.consume().flowOn(coroutineDispatcher)
    }
}