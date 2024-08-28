package com.example.coinalculator.ui.coins.presently.card.states

import com.example.coinalculator.ui.coins.domain.Coin

class CoinDetailsStatesMapper() {
    fun toCoinCardStates(coin: Coin): CoinCardStates {
        return CoinCardStates(
            name = coin.name,
            image = coin.image,
            price = coin.price,
            pricePercentageChange24h = coin.pricePercentageChange24h,
            priceChange24h = coin.priceChange24h,
            marketCap = coin.marketCap,
            marketCapRank = coin.marketCapRank,
            totalVolume = coin.totalVolume,
            isFavorite = coin.isFavorite
        )
    }

    fun toCoinDetails(coin: CoinCardStates) : Coin {
        return Coin(
            name = coin.name,
            image = coin.image,
            price = coin.price,
            pricePercentageChange24h = coin.pricePercentageChange24h,
            priceChange24h = coin.priceChange24h,
            marketCap = coin.marketCap,
            marketCapRank = coin.marketCapRank,
            totalVolume = coin.totalVolume,
            isFavorite = coin.isFavorite
        )
    }
}