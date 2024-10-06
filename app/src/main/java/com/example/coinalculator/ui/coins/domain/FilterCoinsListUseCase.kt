package com.example.coinalculator.ui.coins.domain

class FilterCoinsListUseCase(
    private val coinsRepository: CoinsRepository
) {
    operator fun invoke(query: String, listV: List<CoinEntity>): List<CoinEntity> {
        return listV.filter {
            it.name.contains(query)
        }
    }
}
