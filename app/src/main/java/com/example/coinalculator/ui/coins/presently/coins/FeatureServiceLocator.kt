package com.example.coinalculator.ui.coins.presently.coins

import androidx.lifecycle.ViewModelProvider
import com.example.coinalculator.ServiceLocator
import com.example.coinalculator.ui.coins.presently.coins.model.CoinStateMapper

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