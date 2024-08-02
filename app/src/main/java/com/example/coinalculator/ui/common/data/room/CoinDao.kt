package com.example.coinalculator.ui.common.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.coinalculator.ui.common.data.CoinsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {
    @Query("SELECT * FROM CoinTable WHERE market LIKE :arg")
    fun getALL(arg: String): Flow<List<CoinsEntity>>

    @Insert(entity = CoinTable::class)
    suspend fun insert(newCoin: NewCoin)

    @Query("DELETE FROM CoinTable")
    suspend fun delete()
}