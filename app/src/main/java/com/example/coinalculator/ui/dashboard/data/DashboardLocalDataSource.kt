package com.example.coinalculator.ui.dashboard.data


import android.app.Application
import androidx.room.ColumnInfo
import androidx.room.Dao
import kotlinx.coroutines.flow.Flow
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

class DashboardLocalDataSource(): Application() {
    lateinit var db: CoinsDB

    override fun onCreate() {
        super.onCreate()
        db = Room.inMemoryDatabaseBuilder(
            this,
            CoinsDB::class.java,
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}

@Database(entities = [CoinTable::class], version = 1)
abstract class CoinsDB: RoomDatabase(){
    abstract fun coinDao(): CoinDao
}

@Entity(tableName = "CoinTable")
data class CoinTable (
    @PrimaryKey
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "market") val market: String,
    @ColumnInfo(name = "price") val price: String
)


@Dao
interface CoinDao {
    @Query("SELECT * FROM CoinTable")
    fun getALL(): Flow<List<CoinEntity>>

    @Insert(entity = CoinTable::class)
    suspend fun insert(coinEntity: CoinEntity)

    @Query("DELETE FROM CoinTable")
    suspend fun delete()
}