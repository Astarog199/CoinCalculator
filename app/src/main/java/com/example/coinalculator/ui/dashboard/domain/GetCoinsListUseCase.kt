package com.example.coinalculator.ui.dashboard.domain

import com.example.coinalculator.ui.dashboard.data.DashboardRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class GetCoinsListUseCase(private val repositoryImpl: DashboardRepositoryImpl) {
    private val scope = CoroutineScope(Dispatchers.IO)


}