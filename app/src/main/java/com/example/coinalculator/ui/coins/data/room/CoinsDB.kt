package com.example.coinalculator.ui.coins.data.room


import androidx.room.Database

import androidx.room.RoomDatabase

@Database(entities = [CoinTable::class], version = 1)
abstract class CoinsDB: RoomDatabase(){
    abstract fun coinDao(): CoinDao
}