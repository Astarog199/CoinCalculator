package com.example.coinalculator.ui.coins.presently.card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.coinalculator.ui.coins.domain.ChangeFavoriteStateUseCase
import com.example.coinalculator.ui.coins.domain.ConsumeCoinCardUseCase
import com.example.coinalculator.ui.coins.presently.card.states.CoinDetailsStatesMapper

class CoinCardViewModelFactory(
    private val consumeCoinCard: ConsumeCoinCardUseCase,
    private val coinDetailsStatesMapper: CoinDetailsStatesMapper,
    private val addFavoriteUseCase: ChangeFavoriteStateUseCase,
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
                    consumeCoinCardUseCase = consumeCoinCard,
                    coinDetailsStatesMapper = coinDetailsStatesMapper,
                    addFavoriteUseCase = addFavoriteUseCase,
                    coinName = productId,
                ) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}