package com.example.coinalculator.ui.common.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CoinEntity")
data class CoinEntity (
    @PrimaryKey
//    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "price_percentage_change_24h") val price_percentage_change_24h: Float,
    @ColumnInfo(name = "isFavorite") val isFavorite: Boolean
)