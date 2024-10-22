package com.example.coinalculator.di

import com.example.coinalculator.ui.coins.presently.card.CoinCardComponent
import dagger.Module

@Module(subcomponents = [
        CoinCardComponent::class
    ])
object SubcomponentsModule