package com.example.coinalculator.ui.CoinsList.data

import androidx.room.ColumnInfo


data class CoinsListEntity (
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "market") val market: String,
    @ColumnInfo(name = "price") val price: String
)

