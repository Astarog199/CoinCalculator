package com.example.coinalculator.ui.common.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {
    @Query("SELECT * FROM CoinEntity")
    fun getALL(): Flow<List<CoinEntity>>

    @Insert(entity = CoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(newCoin: NewCoin)

    @Insert(entity = CoinEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(newCoins: List<NewCoin>)

    @Update
    suspend fun update(coin: CoinEntity)

    @Query("DELETE FROM CoinEntity")
    suspend fun delete()

    @Update
    suspend fun changeFavorite (coinTable: CoinEntity)
}