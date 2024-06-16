package com.example.coinalculator.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coinalculator.ui.dashboard.data.DashboardRepositoryImpl
import com.example.coinalculator.ui.dashboard.domain.ConsumeDashboardUseCase
import com.example.coinalculator.ui.dashboard.domain.FilterCoinsListUseCase
import com.example.coinalculator.ui.dashboard.presently.DashboardViewModel

class ViewModelFactory(
    private val consumeDashboardUseCase: ConsumeDashboardUseCase,
    private val filterCoinsListUseCase: FilterCoinsListUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when{
            modelClass.isAssignableFrom(DashboardViewModel::class.java)->{
                @Suppress("UNCHEKED_CAST")
                return DashboardViewModel(consumeDashboardUseCase, filterCoinsListUseCase) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}