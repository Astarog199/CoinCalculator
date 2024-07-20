package com.example.coinalculator.ui.CoinsList.data

import android.app.Application
import com.example.coinalculator.ui.CoinsList.data.room.CoinDao
import kotlinx.coroutines.flow.Flow

import com.example.coinalculator.ui.CoinsList.data.room.CoinsDB


class CoinsListLocalDataSource(private val coinDao: CoinDao): Application()  {
    private lateinit var db: CoinsDB

    fun consume() : Flow<List<CoinsListEntity>> =
         coinDao.getALL()


    suspend fun saveProducts(coinsListEntity: CoinsListEntity) {
        coinDao.insert(coinsListEntity)
    }

//    override fun onCreate() {
//        super.onCreate()
//        db = Room.inMemoryDatabaseBuilder(
//            this,
//            CoinsDB::class.java,
//        )
//            .fallbackToDestructiveMigration()
//            .build()
//    }
}