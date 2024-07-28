package com.example.coinalculator.ui.coins.data

import com.example.coinalculator.ui.common.data.CoinsDto

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

