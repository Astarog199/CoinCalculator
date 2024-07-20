package com.example.coinalculator.ui.dashboard.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.coinalculator.ui.dashboard.data.CoinEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {
    @Query("SELECT * FROM CoinTable")
    fun getALL(): Flow<List<CoinEntity>>

    @Insert(entity = CoinTable::class)
    suspend fun insert(coinEntity: CoinEntity)

    @Query("DELETE FROM CoinTable")
    suspend fun delete()
}