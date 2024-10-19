package com.example.coinalculator.ui.common.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "Coin")
data class Coin (
    @PrimaryKey
    @ColumnInfo(name = "symbol") val symbol: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "price") val price: Float,
    @ColumnInfo(name = "pricePercentageChange24h") val pricePercentageChange24h: Float,
    @ColumnInfo(name = "priceChange24h") val priceChange24h: Float,
    @ColumnInfo(name = "marketCap") val marketCap: Long,
    @ColumnInfo(name = "marketCapRank") val marketCapRank: Int,
    @ColumnInfo(name = "totalVolume") val totalVolume: Long,
    @ColumnInfo(name = "isFavorite") val isFavorite: Boolean
)