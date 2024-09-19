package com.example.coinalculator.ui.common.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {
    @Query("SELECT * FROM Coin")
    fun getALL(): Flow<List<Coin>>

    @Insert(entity = Coin::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(newCoin: NewCoin)

    @Insert(entity = Coin::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(newCoins: List<NewCoin>)

    @Update
    suspend fun update(coin: Coin)

    @Query("DELETE FROM Coin")
    suspend fun delete()

    @Update
    suspend fun changeFavorite (coin: Coin)
}