package com.example.coinalculator.ui.calculator.presently

import com.example.coinalculator.di.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface CalculatorComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create() : CalculatorComponent
    }
    fun inject(fragment: CalculatorFragment)
}