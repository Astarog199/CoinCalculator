package com.example.coinalculator.ui.calculator.presently

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coinalculator.ui.calculator.domain.ConsumeCalculatorUseCase
import com.example.coinalculator.ui.calculator.presently.states.CalcStateMapper

class ViewModelFactory(
    private val consumeCoinsUseCase: ConsumeCalculatorUseCase,
    private val calcStateMapper: CalcStateMapper
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when{
            modelClass.isAssignableFrom(CalculatorViewModel::class.java) ->{
                @Suppress("UNCHEKED_CAST")
                return CalculatorViewModel(
                    consumeCoinsUseCase = consumeCoinsUseCase,
                    calcStateMapper = calcStateMapper
                ) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}