package com.example.coinalculator.ui.calculator.presently

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coinalculator.ui.calculator.domain.ConsumeCoinsUseCase
import com.example.coinalculator.ui.calculator.domain.ConsumeFiatUseCase
import com.example.coinalculator.ui.calculator.presently.states.CalcStateMapper
import javax.inject.Inject

class CalculatorViewModelFactory  @Inject constructor(
    private val consumeFiatUseCase: ConsumeFiatUseCase,
    private val consumeCoinsUseCase: ConsumeCoinsUseCase,
    private val calcStateMapper: CalcStateMapper
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when{
            modelClass.isAssignableFrom(CalculatorViewModel::class.java) ->{
                @Suppress("UNCHEKED_CAST")
                return CalculatorViewModel(
                    consumeFiatUseCase = consumeFiatUseCase,
                    consumeCoinsUseCase = consumeCoinsUseCase,
                    calcStateMapper = calcStateMapper
                ) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}