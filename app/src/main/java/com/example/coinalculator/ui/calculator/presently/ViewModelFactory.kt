package com.example.coinalculator.ui.calculator.presently

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coinalculator.ui.calculator.data.RemoteDataSource
import com.example.coinalculator.ui.calculator.domain.ConsumeCoinsUseCase

class ViewModelFactory(
    private val consumeCoinsUseCase: ConsumeCoinsUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when{
            modelClass.isAssignableFrom(CalculatorViewModel::class.java) ->{
                @Suppress("UNCHEKED_CAST")
                return CalculatorViewModel(
                    consumeCoinsUseCase = consumeCoinsUseCase
                ) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}