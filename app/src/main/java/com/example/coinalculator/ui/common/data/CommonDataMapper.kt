package com.example.coinalculator.ui.common.data

class CommonDataMapper {
    fun toEntity(coinsDto: CoinsDto) : CommonEntity {
        return CommonEntity(
            name = coinsDto.name,
            image = coinsDto.image,
            price = coinsDto.currentPrice.toString(),
            pricePercentageChange24h =  coinsDto.priceChangePercentage24h,
            marketCap = coinsDto.marketCap,
            marketCapRank = coinsDto.marketCapRank,
            priceChange24h = coinsDto.priceChange24h,
            totalVolume = coinsDto.totalVolume,
            isFavorite = false
        )
    }
}

