package com.example.coinalculator.ui.coins.presently.card

import com.example.coinalculator.di.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface CoinCardComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create():CoinCardComponent
    }
    fun inject(fragment: CoinCardFragment)
}