package com.example.coinalculator.ui.Coins.data

import com.example.coinalculator.ui.Coins.data.room.CoinDao
import com.example.coinalculator.ui.Coins.data.room.NewCoin
import kotlinx.coroutines.flow.Flow

//private val dataStore: DataStore<Preferences>
class CoinsLocalDataSource(private val coinDao: CoinDao) {


  fun consume(): Flow<List<CoinsEntity>>  {
     return coinDao.getALL()
  }

    suspend fun save(coin: NewCoin){
        coinDao.insert(coin)
    }



//    fun consume() : Flow<List<CoinsEntity>> = dataStore.data.map ( ::mapProductFromPrefs )
//
//    suspend fun save(coinsEntity: List<CoinsEntity>) {
//        dataStore.edit { prefs -> prefs[productPreferencesKey] = encodeToString(coinsEntity) }
//    }

//    @OptIn(InternalSerializationApi::class)
//    private fun decodeFromString(string: String): List<CoinsEntity> =
//        try {
//            Json.decodeFromString(ListSerializer(CoinsEntity::class.serializer()), string)
//        } catch (e: Exception) {
//            listOf()
//        }
//
//    private fun mapProductFromPrefs(prefs: Preferences): List<CoinsEntity> =
//        prefs[productPreferencesKey]
//            ?.takeIf(String::isNotEmpty)
//            ?.let(this::decodeFromString) ?: listOf()
//
//    private val productPreferencesKey = stringPreferencesKey(KEY)
//
//    @OptIn(InternalSerializationApi::class)
//    private fun encodeToString(products: List<CoinsEntity>): String =
//        Json.encodeToString(
//            ListSerializer(CoinsEntity::class.serializer()),
//            products,
//        )
//
//    private companion object {
//        const val KEY = "key"
//    }
}