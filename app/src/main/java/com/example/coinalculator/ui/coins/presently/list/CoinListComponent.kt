package com.example.coinalculator.ui.coins.presently.list

import com.example.coinalculator.di.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface CoinListComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create() : CoinListComponent
    }
    fun inject(fragment: CoinListFragment)
}