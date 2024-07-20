package com.example.coinalculator.ui.CoinsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coinalculator.ui.CoinsList.domain.ConsumeDashboardUseCase
import com.example.coinalculator.ui.CoinsList.domain.FilterCoinsListUseCase
import com.example.coinalculator.ui.CoinsList.presently.CoinVOMapper
import com.example.coinalculator.ui.CoinsList.presently.DashboardViewModel

class ViewModelFactory(
    private val consumeDashboardUseCase: ConsumeDashboardUseCase,
    private val filterCoinsListUseCase: FilterCoinsListUseCase,
    private val coinVOMapper: CoinVOMapper
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when{
            modelClass.isAssignableFrom(DashboardViewModel::class.java)->{
                @Suppress("UNCHEKED_CAST")
                return DashboardViewModel(
                    consumeDashboardUseCase,
                    filterCoinsListUseCase,
                    coinVOMapper
                    ) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}