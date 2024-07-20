package com.example.coinalculator.ui.dashboard.data

import android.app.Application
import androidx.room.Room
import com.example.coinalculator.ui.dashboard.data.room.CoinDao
import kotlinx.coroutines.flow.Flow

import com.example.coinalculator.ui.dashboard.data.room.CoinsDB
import kotlinx.coroutines.flow.map


class DashboardLocalDataSource(private val coinDao: CoinDao): Application()  {
    private lateinit var db: CoinsDB

    fun consume() : Flow<List<CoinEntity>> =
         coinDao.getALL()


    suspend fun saveProducts(coinEntity: CoinEntity) {
        coinDao.insert(coinEntity)
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