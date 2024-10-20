package com.example.coinalculator.ui.coins.presently.card.states

import com.example.coinalculator.ui.coins.domain.CoinEntity
import kotlinx.coroutines.flow.Flow

class CoinDetailsStatesMapper() {
    fun toCoinCardStates(coin: CoinEntity): CoinCardStates {
        return CoinCardStates(
            symbol = coin.symbol,
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

    fun toCoinDetails(coin: CoinCardStates) : CoinEntity {
        return CoinEntity(
            symbol = coin.symbol,
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