package com.example.coinalculator.ui.coins.presently.card

import androidx.lifecycle.ViewModelProvider
import com.example.coinalculator.ServiceLocator
import com.example.coinalculator.ui.coins.presently.card.states.CoinDetailsStatesMapper

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