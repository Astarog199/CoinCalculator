package com.example.coinalculator.ui.common.data

class CommonDataMapper {
    fun toEntity(coinsDto: CoinsDto) : CommonEntity {
        return CommonEntity(
            id = 0,
            name = coinsDto.name,
            image = coinsDto.image,
            price = coinsDto.currentPrice.toString(),
            price_percentage_change_24h =  coinsDto.priceChangePercentage24h,
            isFavorite = false
        )
    }
}

