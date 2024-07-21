package com.example.coinalculator.ui.Coins

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coinalculator.ui.Coins.domain.ConsumeCoinsUseCase
import com.example.coinalculator.ui.Coins.domain.FilterCoinsListUseCase
import com.example.coinalculator.ui.Coins.presently.CoinVOMapper
import com.example.coinalculator.ui.Coins.presently.CoinsViewModel

class ViewModelFactory(
    private val consumeCoinsUseCase: ConsumeCoinsUseCase,
    private val filterCoinsListUseCase: FilterCoinsListUseCase,
    private val coinVOMapper: CoinVOMapper
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when{
            modelClass.isAssignableFrom(CoinsViewModel::class.java)->{
                @Suppress("UNCHEKED_CAST")
                return CoinsViewModel(
                    consumeCoinsUseCase,
                    filterCoinsListUseCase,
                    coinVOMapper
                    ) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}