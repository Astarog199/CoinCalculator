package com.example.coinalculator.ui.Coins.data

import com.example.coinalculator.ui.Coins.data.room.CoinDao
import com.example.coinalculator.ui.Coins.data.room.NewCoin
import kotlinx.coroutines.flow.Flow

class CoinsLocalDataSource(private val coinDao: CoinDao) {


  fun consume(): Flow<List<CoinsEntity>>  {
     return coinDao.getALL(arg = "Binance (Futures)")
  }

    suspend fun save(coin: NewCoin){
        coinDao.insert(coin)
    }
}