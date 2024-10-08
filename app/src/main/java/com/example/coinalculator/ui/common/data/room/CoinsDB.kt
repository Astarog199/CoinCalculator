package com.example.coinalculator.ui.common.data.room


import androidx.room.Database

import androidx.room.RoomDatabase

@Database(entities = [Coin::class], version = 1)
abstract class CoinsDB: RoomDatabase(){
    abstract fun coinDao(): CoinDao
}