package com.example.coinalculator.ui.coins.domain

import com.example.coinalculator.ui.common.data.CommonRepositoryImpl
import kotlinx.coroutines.flow.map


class FilterCoinsListUseCase(
    private val coinsRepository: CommonRepositoryImpl
) {
    operator fun invoke(query: String, listV: List<CoinEntity>): List<CoinEntity> {
        return listV.filter {
            it.name.contains(query)
        }
    }
}
