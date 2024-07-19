package com.example.coinalculator.ui.dashboard.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


data class CoinEntity (
    @PrimaryKey
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "market") val market: String,
    @ColumnInfo(name = "price") val price: String
)

