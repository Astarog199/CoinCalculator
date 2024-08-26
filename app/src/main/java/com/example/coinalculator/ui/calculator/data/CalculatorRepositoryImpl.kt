package com.example.coinalculator.ui.calculator.data

import com.example.coinalculator.ui.calculator.data.models.DataMapper
import com.example.coinalculator.ui.calculator.domain.CalculatorRepository
import com.example.coinalculator.ui.calculator.domain.CoinCalculator
import com.example.coinalculator.ui.common.data.CommonDataMapper
import com.example.coinalculator.ui.common.data.CommonRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CalculatorRepositoryImpl(
    private val repository: CalculatorRemoteDataSource,
    private val coinsRemoteDataSource: CommonRemoteDataSource,
    private val calculatorLocalDataSource: CalculatorLocalDataSource,
    private val dataMapper: DataMapper,
    private val calculatorDomainMapper: CalculatorDomainMapper,
    private val coroutineDispatcher: CoroutineDispatcher,
) : CalculatorRepository {
    private val scope = CoroutineScope(SupervisorJob() + coroutineDispatcher)

    override  fun consumeCoins(): Flow<List<CoinCalculator>> {
        return createList().map { coins ->
            coins.map(calculatorDomainMapper::fromEntity)
        }
    }

    private fun createList(): Flow<List<CalculatorEntity>> {
        scope.launch {
            val list = mutableListOf<CalculatorEntity>()
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
