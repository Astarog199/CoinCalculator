package com.example.coinalculator.di

import android.content.Context
import com.example.coinalculator.ui.calculator.presently.CalculatorComponent
import com.example.coinalculator.ui.coins.presently.card.CoinCardComponent
import com.example.coinalculator.ui.coins.presently.list.CoinListComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    NetworkModule::class,
    DataModule::class,
    SubcomponentsModule::class
        ])
interface AppComponent {
    fun coinCardFragmentFactory(): CoinCardComponent.Factory
    fun coinListFragmentFactory(): CoinListComponent.Factory
    fun calculatorFragmentFactory(): CalculatorComponent.Factory

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context): AppComponent
    }
}