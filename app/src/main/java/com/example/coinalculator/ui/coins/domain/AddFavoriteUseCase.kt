package com.example.coinalculator.ui.coins.domain

import com.example.coinalculator.ui.common.data.CommonRepository


class AddFavoriteUseCase(
    private val coinsRepository: CoinsRepository,
    ) {
    suspend operator fun invoke(coin: CoinDetails){
        coinsRepository.addFavorite(coin)
    }
}