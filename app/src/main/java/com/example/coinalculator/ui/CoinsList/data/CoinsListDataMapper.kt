package com.example.coinalculator.ui.CoinsList.data

import com.example.coinalculator.ui.CoinsList.domain.Coin

class CoinsListDataMapper {
    fun toEntity(coinsListDto: CoinsListDto) : CoinsListEntity{
        return CoinsListEntity(
            name = coinsListDto.index_id.toString(),
            market = coinsListDto.market.toString(),
            price = coinsListDto.price
        )
    }

    fun fromEntity(coinsListEntity: CoinsListEntity) : Coin {
        return Coin(
            name = coinsListEntity.name,
            market = coinsListEntity.market,
            price = coinsListEntity.price,
        )
    }
}

