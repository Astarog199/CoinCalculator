package com.example.coinalculator.ui.Coins.data

class CoinsDataMapper {
    fun toEntity(coinsDto: CoinsDto) : CoinsEntity{
        return CoinsEntity(
            id = 0,
            name = coinsDto.index_id.toString(),
            market = coinsDto.market.toString(),
            price = coinsDto.price
        )
    }
}

