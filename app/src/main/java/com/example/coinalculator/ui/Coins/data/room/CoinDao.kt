package com.example.coinalculator.ui.Coins.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.coinalculator.ui.Coins.data.CoinsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {
    @Query("SELECT * FROM CoinTable")
    fun getALL(): Flow<List<CoinsEntity>>

    @Insert(entity = CoinTable::class)
    suspend fun insert(coinsEntity: CoinsEntity)

    @Query("DELETE FROM CoinTable")
    suspend fun delete()
}