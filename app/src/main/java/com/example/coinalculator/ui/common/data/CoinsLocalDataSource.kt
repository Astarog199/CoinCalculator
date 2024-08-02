package com.example.coinalculator.ui.common.data

import com.example.coinalculator.ui.common.data.room.CoinDao
import com.example.coinalculator.ui.common.data.room.NewCoin
import kotlinx.coroutines.flow.Flow

class CoinsLocalDataSource(private val coinDao: CoinDao) {


  fun consume(): Flow<List<CoinsEntity>>  {
     return coinDao.getALL(arg = "Binance (Futures)")
  }

    suspend fun save(coin: NewCoin){
        coinDao.insert(coin)
    }
}