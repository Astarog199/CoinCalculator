package com.example.coinalculator.ui.coins.domain

import com.example.coinalculator.ui.common.data.CommonRepository
import javax.inject.Inject

class FilterCoinsListUseCase @Inject constructor(
    private val repository: CommonRepository
) {
    operator fun invoke(query: String, listV: List<CoinEntity>): List<CoinEntity> {
        return listV.filter {
            it.name.contains(query)
        }
    }
}
