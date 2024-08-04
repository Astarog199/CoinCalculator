package com.example.coinalculator.ui.common.data.room

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class NewCoin(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int? = null,
    @ColumnInfo(name = "name")val name: String,
    @ColumnInfo(name = "market")val market: String,
    @ColumnInfo(name = "price")val price: String,
    @ColumnInfo(name = "price_percentage_change_24h")val price_percentage_change_24h: Float,
    @ColumnInfo(name = "isFavorite")val isFavorite: Boolean = false
)
