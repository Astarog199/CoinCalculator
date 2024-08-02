package com.example.coinalculator.ui.card.presentation

import androidx.lifecycle.ViewModelProvider
import com.example.coinalculator.ServiceLocator
import com.example.coinalculator.ui.card.presentation.card.CoinCardViewModelFactory
import com.example.coinalculator.ui.card.presentation.card.states.CoinDetailsStatesMapper

object FeatureServiceLocator {

    fun provideCoinCardViewModelFactory(productId: String) : ViewModelProvider.Factory{
        return CoinCardViewModelFactory(
            consumeCoinCard = ServiceLocator.provideConsumeCoinCard(),
            coinDetailsStatesMapper =  provideCoinDetailsStatesMapper(),
            productId = productId
        )
    }

    private fun provideCoinDetailsStatesMapper() : CoinDetailsStatesMapper {
        return CoinDetailsStatesMapper()
    }
}