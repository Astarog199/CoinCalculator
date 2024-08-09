package com.example.coinalculator.ui.coins.domain

import com.example.coinalculator.ui.common.data.CommonRepository


class AddFavoriteUseCase(
    private val coinsRepository: CommonRepository,
    private val cardMapper: CoinsMapper
    ) {
    suspend operator fun invoke(coin: CoinDetails){
        val value = cardMapper.toEntity(coin)
        coinsRepository.addFavorite(value)
    }
}