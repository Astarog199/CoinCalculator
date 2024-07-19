package com.example.coinalculator.ui.dashboard.data

import com.example.coinalculator.ui.dashboard.domain.Coin

class CoinDataMapper {
    fun toEntity(coinDto: CoinDto) : CoinEntity{
        return CoinEntity(
            name = coinDto.index_id.toString(),
            market = coinDto.market.toString(),
            price = coinDto.price
        )
    }

    fun fromEntity( coinEntity: CoinEntity) : Coin {
        return Coin(
            name = coinEntity.name,
            market = coinEntity.market,
            price = coinEntity.price,
        )
    }
}

