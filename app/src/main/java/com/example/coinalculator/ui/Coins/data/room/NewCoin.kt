package com.example.coinalculator.ui.Coins.data.room

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class NewCoin(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int? = null,
    @ColumnInfo(name = "name")val name: String,
    @ColumnInfo(name = "market")val market: String,
    @ColumnInfo(name = "price")val price: String
)
