package com.example.coinalculator.ui.dashboard.domain

import com.example.coinalculator.ui.dashboard.data.CoinModel
import com.example.coinalculator.ui.dashboard.data.ElementList

interface DashboardRepository {
    suspend fun getCoin(): CoinModel

    suspend fun getList() : List<ElementList>
}