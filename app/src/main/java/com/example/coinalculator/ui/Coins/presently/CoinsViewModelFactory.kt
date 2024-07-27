package com.example.coinalculator.ui.Coins.presently.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.coinalculator.ui.Coins.domain.ConsumeCoinsUseCase
import com.example.coinalculator.ui.Coins.domain.FilterCoinsListUseCase

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