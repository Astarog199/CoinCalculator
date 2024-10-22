package com.example.coinalculator.ui.calculator.data

import com.example.coinalculator.ui.calculator.data.models.CalculatorDataEntity
import com.example.coinalculator.ui.calculator.domain.DomainEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class CalculatorRepository @Inject constructor(
    private val calculatorRemoteDataSource: CalculatorRemoteDataSource,
    private val dataMapper: DataMapper,
    private val calculatorLocalDataSource: CalculatorLocalDataSource,
    private val coroutineDispatcher: CoroutineDispatcher
) {
    private val scope = CoroutineScope(SupervisorJob() + coroutineDispatcher)

    private suspend fun getRUB(): CalculatorDataEntity {
        var rub = CalculatorDataEntity("rub",0f)
        var tryGetRub = 0
        while (rub.price == 0f && tryGetRub < 6){
            tryGetRub++
            rub = dataMapper.rubToDataEntity(calculatorRemoteDataSource.getRub(), "rub")
            delay(1000)
        }
        return rub
    }

    private fun getUsd(): CalculatorDataEntity{
        return CalculatorDataEntity(symbol = "usd", price = 1f)
    }

    fun consumeCalculatorFiat(): Flow<List<DomainEntity>> {
        return saveToLocalDataSource().map { it.map(dataMapper::toDomainEntity) }
    }

    private fun saveToLocalDataSource(): Flow<List<CalculatorDataEntity>> {
        scope.launch {
            val fiat  = mutableListOf<CalculatorDataEntity>()
            fiat.add(getRUB())
            fiat.add(getUsd())
            calculatorLocalDataSource.save(fiat)
        }

        return calculatorLocalDataSource.consume().flowOn(coroutineDispatcher)
    }
}