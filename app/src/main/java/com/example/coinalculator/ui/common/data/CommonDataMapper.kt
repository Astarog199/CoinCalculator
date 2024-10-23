package com.example.coinalculator.ui.common.data

import com.example.coinalculator.ui.calculator.domain.DomainEntity
import com.example.coinalculator.ui.coins.domain.CoinEntity
import com.example.coinalculator.ui.common.data.room.Coin
import com.example.coinalculator.ui.common.data.room.NewCoin
import javax.inject.Inject

class CommonDataMapper @Inject constructor() {
    fun toNewCoin(coin: CoinsDto): NewCoin {
        return NewCoin(
            symbol = coin.symbol,
            name = coin.name,
            image = coin.image,
            price = coin.currentPrice,
            pricePercentageChange24h = coin.priceChangePercentage24h,
            priceChange24h = coin.priceChange24h,
            marketCap = coin.marketCap,
            marketCapRank = coin.marketCapRank,
            totalVolume = coin.totalVolume,
            high24h = coin.high24h,
            low24h = coin.low24h,
            circulatingSupply =coin.circulatingSupply,
            totalSupply = coin.totalSupply,
            maxSupply = coin.maxSupply,
        )
    }

    fun toCoinEntity(coin: Coin): CoinEntity {
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
            high24h = coin.high24h,
            low24h = coin.low24h,
            circulatingSupply = coin.circulatingSupply,
            totalSupply = coin.totalSupply,
            maxSupply = coin.maxSupply,
            isFavorite = coin.isFavorite
        )
    }

    fun toCoin(coinEntity: CoinEntity): Coin {
        return Coin(
            symbol = coinEntity.symbol,
            name = coinEntity.name,
            image = coinEntity.image,
            price = coinEntity.price,
            pricePercentageChange24h = coinEntity.pricePercentageChange24h,
            priceChange24h = coinEntity.priceChange24h,
            marketCap = coinEntity.marketCap,
            marketCapRank = coinEntity.marketCapRank,
            totalVolume = coinEntity.totalVolume,
            high24h = coinEntity.high24h,
            low24h = coinEntity.low24h,
            circulatingSupply =coinEntity.circulatingSupply,
            totalSupply = coinEntity.totalSupply,
            maxSupply = coinEntity.maxSupply,
            isFavorite = coinEntity.isFavorite
        )
    }

    fun toCalculator(entity: Coin): DomainEntity {
        return DomainEntity(
            symbol = entity.symbol,
            price = entity.price
        )
    }
}