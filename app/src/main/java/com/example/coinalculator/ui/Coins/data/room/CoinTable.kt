package com.example.coinalculator.ui.Coins.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CoinTable")
data class CoinTable (
    @PrimaryKey
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "market") val market: String,
    @ColumnInfo(name = "price") val price: String
)