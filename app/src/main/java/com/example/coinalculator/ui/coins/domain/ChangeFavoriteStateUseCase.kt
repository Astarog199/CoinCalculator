package com.example.coinalculator.ui.coins.domain

import com.example.coinalculator.ui.common.data.CommonRepository
import javax.inject.Inject


class ChangeFavoriteStateUseCase @Inject constructor(
    private val repository: CommonRepository
    ) {
    suspend operator fun invoke(coin: CoinEntity){
        repository.changeFavoriteState(coin)
    }
}