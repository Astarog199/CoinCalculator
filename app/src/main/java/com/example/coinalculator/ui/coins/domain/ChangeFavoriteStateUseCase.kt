package com.example.coinalculator.ui.coins.domain

class ChangeFavoriteStateUseCase(
    private val coinsRepository: CoinsRepository,
    ) {
    suspend operator fun invoke(coin: CoinEntity){
        coinsRepository.changeFavoriteState(coin)
    }
}