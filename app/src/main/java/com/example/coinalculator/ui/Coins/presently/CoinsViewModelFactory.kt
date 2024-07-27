package com.example.coinalculator.ui.Coins.presently

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coinalculator.ui.Coins.domain.ConsumeCoinsUseCase
import com.example.coinalculator.ui.Coins.domain.FilterCoinsListUseCase
import com.example.coinalculator.ui.Coins.presently.model.CoinStateMapper

class CoinsViewModelFactory(
    private val coinStateMapper: CoinStateMapper,
    private val consumeCoinsUseCase: ConsumeCoinsUseCase,
    private val filterCoinsListUseCase: FilterCoinsListUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CoinViewModel(
            coinStateMapper = coinStateMapper,
            consumeCoinsUseCase = consumeCoinsUseCase,
            filterCoinsListUseCase = filterCoinsListUseCase
        ) as T
    }
}