package com.example.coinalculator.ui.calculator.presently

import androidx.lifecycle.ViewModelProvider
import com.example.coinalculator.ServiceLocator.provideConsumeCalculatorUseCase
import com.example.coinalculator.ui.calculator.presently.states.CalcStateMapper

object FeatureServiceLocator {

    fun provideViewModel() : ViewModelProvider.Factory{
        return ViewModelFactory(
            consumeCoinsUseCase = provideConsumeCalculatorUseCase(),
            calcStateMapper = CalcStateMapper()
        )
    }
}