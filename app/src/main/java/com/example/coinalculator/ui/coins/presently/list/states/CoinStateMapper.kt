package com.example.coinalculator.ui.coins.presently.list.states

import com.example.coinalculator.ui.coins.domain.CoinEntity
import javax.inject.Inject

class CoinStateMapper @Inject constructor() {
    fun toCoinState(coin: CoinEntity) : CoinState {
        return CoinState(
            name = coin.name,
            image = coin.image,
            price = coin.price,
            priceChange24h = coin.priceChange24h,
            pricePercentageChange24h = coin.pricePercentageChange24h,
            isFavorite = coin.isFavorite
        )
    }
}