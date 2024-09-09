package com.example.coinalculator.ui.common.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {
    @Query("SELECT * FROM Entity")
    fun getALL(): Flow<List<Entity>>

    @Insert(entity = Entity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(newCoin: NewCoin)

    @Insert(entity = Entity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(newCoins: List<NewCoin>)

    @Update
    suspend fun update(coin: Entity)

    @Query("DELETE FROM Entity")
    suspend fun delete()

    @Update
    suspend fun changeFavorite (coinTable: Entity)
}