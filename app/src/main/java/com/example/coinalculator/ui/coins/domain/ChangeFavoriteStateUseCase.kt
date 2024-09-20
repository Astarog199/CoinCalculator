package com.example.coinalculator.ui.coins.domain

import com.example.coinalculator.ui.common.data.CommonRepositoryImpl

class ChangeFavoriteStateUseCase(
    private val coinsRepository: CommonRepositoryImpl
    ) {
    suspend operator fun invoke(coin: CoinEntity){
        coinsRepository.changeFavoriteState(coin)
    }
}