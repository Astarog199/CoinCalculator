package com.example.coinalculator.ui.Coins.data.room


import androidx.room.Database

import androidx.room.RoomDatabase

@Database(entities = [CoinTable::class], version = 1)
abstract class CoinsDB: RoomDatabase(){
    abstract fun coinDao(): CoinDao

//    companion object{
//        @Volatile
//        private var INSTANCE: CoinsDB? = null
//
//        fun getInstance(context: Context): CoinsDB {
//
//            var tempInstance = INSTANCE
//            if(tempInstance != null) {
//                return tempInstance
//            }
//
//            synchronized(this) {
//                val instance =Room.inMemoryDatabaseBuilder(context.applicationContext,
//                    CoinsDB::class.java,
//                    )
//                    .fallbackToDestructiveMigration()
//                    .build()
//
//                INSTANCE = instance
//                return instance
//            }
//        }



}