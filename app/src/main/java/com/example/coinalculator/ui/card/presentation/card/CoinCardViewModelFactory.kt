package com.example.coinalculator.ui.card.presentation.card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.coinalculator.ui.card.domain.ConsumeCoinCard
import com.example.coinalculator.ui.card.presentation.card.states.CoinDetailsStatesMapper

class CoinCardViewModelFactory(
    private val consumeCoinCard: ConsumeCoinCard,
    private val coinDetailsStatesMapper: CoinDetailsStatesMapper,
    private val productId: String
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
        ): T {
        when{
            modelClass.isAssignableFrom(CoinCardViewModel::class.java) -> {
                @Suppress("UNCHEKED_CAST")
                return CoinCardViewModel(
                    consumeCoinCard = consumeCoinCard,
                    coinDetailsStatesMapper = coinDetailsStatesMapper,
                    productId = productId,
                ) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}