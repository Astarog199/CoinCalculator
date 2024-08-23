package com.example.coinalculator.ui.calculator.data

import androidx.compose.runtime.toMutableStateList
import com.example.coinalculator.ui.calculator.data.models.DataMapper
import com.example.coinalculator.ui.calculator.domain.CalculatorRepository
import com.example.coinalculator.ui.calculator.domain.CoinCalculator
import com.example.coinalculator.ui.common.data.CoinsDataMapper
import com.example.coinalculator.ui.common.data.CoinsRemoteDataSource
import com.example.coinalculator.ui.common.data.CommonRepository
import com.example.coinalculator.ui.common.data.room.CoinEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RepositoryImpl(
    private val repository: RemoteDataSource,
    private val coinsRemoteDataSource: CoinsRemoteDataSource,
    private val coinsDataMapper: CoinsDataMapper,
    private val calculatorLocalDataSource: CalculatorLocalDataSource,
    private val dataMapper: DataMapper,
    private val calculatorDomainMapper: CalculatorDomainMapper,
    private val coroutineDispatcher: CoroutineDispatcher,
) : CalculatorRepository {
    private val scope = CoroutineScope(SupervisorJob() + coroutineDispatcher)
    private var list = mutableListOf<CalculatorEntity>()

    override suspend fun consumeCoins(): Flow<List<CoinCalculator>> {

        return createList().map { coins ->
            coins.map(calculatorDomainMapper::fromEntity)
        }
    }

    private fun createList(): Flow<List<CalculatorEntity>> {
        scope.launch {
            val coins = coinsRemoteDataSource.getList()
                .map(dataMapper::toEntity)

            list.add(getRUB())

            coins.map { coin ->
                if (coin.name == "usdt" || coin.name == "btc"  || coin.name =="eth" || coin.name =="ton"){
                    list.add(coin)
                }
            }

            calculatorLocalDataSource.saveCoin(list)
        }

        return calculatorLocalDataSource.consumeCoins().flowOn(coroutineDispatcher)
    }


    private suspend fun getRUB(): CalculatorEntity {
        return dataMapper.rubToEntity(repository.getRub(), "rub")
    }
}
