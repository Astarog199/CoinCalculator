package com.example.coinalculator.ui.coins.presently.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coinalculator.ui.coins.domain.ConsumeCoinListUseCase
import com.example.coinalculator.ui.coins.domain.FilterCoinsListUseCase
import com.example.coinalculator.ui.coins.presently.list.states.CoinStateMapper

class CoinListViewModelFactory(
    private val coinStateMapper: CoinStateMapper,
    private val consumeCoinsUseCase: ConsumeCoinListUseCase,
    private val filterCoinsListUseCase: FilterCoinsListUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CoinListViewModel(
            coinStateMapper = coinStateMapper,
            consumeCoinsUseCase = consumeCoinsUseCase,
            filterCoinsListUseCase = filterCoinsListUseCase
        ) as T
    }
}