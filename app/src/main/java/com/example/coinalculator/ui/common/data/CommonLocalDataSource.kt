package com.example.coinalculator.ui.common.data

import com.example.coinalculator.ui.common.data.room.CoinDao
import com.example.coinalculator.ui.common.data.room.Entity
import com.example.coinalculator.ui.common.data.room.NewCoin
import kotlinx.coroutines.flow.Flow

class CommonLocalDataSource(private val coinDao: CoinDao) {

  fun consume(): Flow<List<Entity>>  {
     return coinDao.getALL()
  }

    suspend fun save(coin: NewCoin){
        coinDao.insert(coin)
    }

    suspend fun saveMany(coins: List<NewCoin>){
        coinDao.insertMany(coins)
    }

    suspend fun updateCoin(coin: Entity) {
        coinDao.update(coin)
    }

    suspend fun addFavorite(coin: Entity){
        coinDao.changeFavorite(coin)
    }
}