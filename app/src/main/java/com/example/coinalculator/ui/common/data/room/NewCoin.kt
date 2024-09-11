package com.example.coinalculator.ui.common.data.room

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class NewCoin(
    @PrimaryKey
    @ColumnInfo(name = "name")val name: String,
    @ColumnInfo(name = "image")val image: String,
    @ColumnInfo(name = "price")val price: Float,
    @ColumnInfo(name = "price_percentage_change_24h")val price_percentage_change_24h: Float,
    @ColumnInfo(name = "price_change_24h") val priceChange24h: Float,
    @ColumnInfo(name = "market_cap") val marketCap: Long,
    @ColumnInfo(name = "market_cap_rank") val marketCapRank: Int,
    @ColumnInfo(name = "total_volume") val totalVolume: Long,
    @ColumnInfo(name = "isFavorite")val isFavorite: Boolean = false
)
