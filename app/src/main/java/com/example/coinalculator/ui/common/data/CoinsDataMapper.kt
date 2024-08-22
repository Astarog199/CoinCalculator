package com.example.coinalculator.ui.common.data

class CoinsDataMapper {
    fun toEntity(coinsDto: CoinsDto) : CoinsEntity {
        return CoinsEntity(
            id = 0,
            name = coinsDto.name,
            image = coinsDto.image,
            price = coinsDto.currentPrice.toString(),
            price_percentage_change_24h =  coinsDto.priceChangePercentage24h,
            isFavorite = false
        )
    }
}

