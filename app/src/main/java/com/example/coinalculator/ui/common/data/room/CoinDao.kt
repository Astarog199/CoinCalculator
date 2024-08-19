package com.example.coinalculator.ui.common.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.coinalculator.ui.common.data.CoinsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {
    @Query("SELECT * FROM CoinEntity WHERE market LIKE :arg")
    fun getALL(arg: String): Flow<List<CoinEntity>>

    @Insert(entity = CoinEntity::class)
    suspend fun insert(newCoin: NewCoin)

    @Insert(entity = CoinEntity::class)
    suspend fun insertMany(newCoins: List<NewCoin>)

    @Query("DELETE FROM CoinEntity")
    suspend fun delete()

    @Update
    suspend fun changeFavorite (coinTable: CoinEntity)
}