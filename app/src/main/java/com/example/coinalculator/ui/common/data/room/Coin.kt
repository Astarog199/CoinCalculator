package com.example.coinalculator.ui.common.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Coin")
data class Coin (
    @PrimaryKey
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "price") val price: Float,
    @ColumnInfo(name = "price_percentage_change_24h") val price_percentage_change_24h: Float,
    @ColumnInfo(name = "price_change_24h") val price_change_24h: Float,
    @ColumnInfo(name = "market_cap") val market_cap: Long,
    @ColumnInfo(name = "market_cap_rank") val market_cap_rank: Int,
    @ColumnInfo(name = "total_volume") val total_volume: Long,
    @ColumnInfo(name = "isFavorite") val isFavorite: Boolean
)