package com.example.coinalculator.ui.calculator.data

import com.example.coinalculator.ui.calculator.domain.CalculatorRepository
import com.example.coinalculator.ui.calculator.domain.CoinCalculator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class RepositoryImpl(
    private val repository: RemoteDataSource,
    private val calculatorLocalDataSource: CalculatorLocalDataSource,
    private val calculatorDataMapper: CalculatorDataMapper,
    private val calculatorDomainMapper: CalculatorDomainMapper,
    private val coroutineDispatcher: CoroutineDispatcher,
) : CalculatorRepository {

    private val scope = CoroutineScope(SupervisorJob() + coroutineDispatcher)

    override fun consumeCoins(): Flow<List<CoinCalculator>> {
        return saveCoins().map { coin ->
            coin.map(calculatorDomainMapper :: fromEntity)
        }
    }

        fun saveCoins(): Flow<List<CalculatorEntity>> {
        scope.launch {
            val coins = repository.getCoins()
            calculatorLocalDataSource.saveCoin(
                calculatorDataMapper.toEntity(coins)
            )
        }

        return calculatorLocalDataSource.consumeCoins().flowOn(coroutineDispatcher)
    }


}
