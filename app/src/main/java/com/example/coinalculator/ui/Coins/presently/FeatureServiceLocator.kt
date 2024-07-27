package com.example.coinalculator.ui.Coins.presently

import androidx.lifecycle.ViewModelProvider
import com.example.coinalculator.ui.Coins.ServiceLocator
import com.example.coinalculator.ui.Coins.presently.model.CoinStateMapper

object FeatureServiceLocator {

    fun provideCoinsViewModelFactory() : ViewModelProvider.Factory{
        return CoinsViewModelFactory(
            consumeCoinsUseCase = ServiceLocator.provideConsumeDashboardUseCase(),
            filterCoinsListUseCase = ServiceLocator.provideFilterCoinsListUseCase(),
            coinStateMapper = provideCoinStateMapper()
        )
    }

    private fun provideCoinStateMapper() : CoinStateMapper {
        return CoinStateMapper()
    }
}