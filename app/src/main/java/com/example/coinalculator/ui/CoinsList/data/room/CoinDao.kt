package com.example.coinalculator.ui.CoinsList.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.coinalculator.ui.CoinsList.data.CoinsListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {
    @Query("SELECT * FROM CoinTable")
    fun getALL(): Flow<List<CoinsListEntity>>

    @Insert(entity = CoinTable::class)
    suspend fun insert(coinsListEntity: CoinsListEntity)

    @Query("DELETE FROM CoinTable")
    suspend fun delete()
}