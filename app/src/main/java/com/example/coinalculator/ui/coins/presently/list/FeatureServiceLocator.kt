package com.example.coinalculator.ui.coins.presently.list

import androidx.lifecycle.ViewModelProvider
import com.example.coinalculator.ServiceLocator
import com.example.coinalculator.ui.coins.presently.list.states.CoinStateMapper

object FeatureServiceLocator {

    fun provideCoinsViewModelFactory() : ViewModelProvider.Factory{
        return CoinListViewModelFactory(
            consumeCoinsUseCase = ServiceLocator.provideConsumeDashboardUseCase(),
            filterCoinsListUseCase = ServiceLocator.provideFilterCoinsListUseCase(),
            coinStateMapper = provideCoinStateMapper()
        )
    }

    private fun provideCoinStateMapper() : CoinStateMapper {
        return CoinStateMapper()
    }
}