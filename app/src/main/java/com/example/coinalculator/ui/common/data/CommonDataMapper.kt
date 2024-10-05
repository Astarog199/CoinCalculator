package com.example.coinalculator.ui.common.data

import com.example.coinalculator.ui.calculator.domain.DomainEntity
import com.example.coinalculator.ui.coins.domain.CoinEntity
import com.example.coinalculator.ui.common.data.room.Coin

class CommonDataMapper {
    fun toCoinEntity(coin: Coin) : CoinEntity {
        return CoinEntity(
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

    fun toCoin(coinEntity: CoinEntity) : Coin {
        return Coin(
            name = coinEntity.name,
            image = coinEntity.image,
            price = coinEntity.price,
            pricePercentageChange24h = coinEntity.pricePercentageChange24h,
            priceChange24h = coinEntity.priceChange24h,
            marketCap = coinEntity.marketCap,
            marketCapRank = coinEntity.marketCapRank,
            totalVolume = coinEntity.totalVolume,
            isFavorite = coinEntity.isFavorite
        )
    }

    fun toCalculator (entity: Coin) : DomainEntity {
            return DomainEntity(
            name = entity.name,
            price = entity.price
        )
    }

    fun rubToEntity(coin: RubDTO, name: String) : DomainEntity {
        return DomainEntity(
            name = name,
            price = coin.price.usd
        )
    }
}

