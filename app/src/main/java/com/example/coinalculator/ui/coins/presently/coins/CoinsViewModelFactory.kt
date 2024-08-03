package com.example.coinalculator.ui.coins.presently.coins

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coinalculator.ui.coins.domain.ConsumeCoinsUseCase
import com.example.coinalculator.ui.coins.domain.FilterCoinsListUseCase
import com.example.coinalculator.ui.coins.presently.coins.model.CoinStateMapper

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