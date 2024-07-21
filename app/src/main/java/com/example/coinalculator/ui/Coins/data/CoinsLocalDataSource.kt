package com.example.coinalculator.ui.Coins.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer



class CoinsLocalDataSource(private val dataStore: DataStore<Preferences>,) {

    fun consume() : Flow<List<CoinsEntity>> = dataStore.data.map ( ::mapProductFromPrefs )


    suspend fun save(coinsEntity: List<CoinsEntity>) {
        dataStore.edit { prefs -> prefs[productPreferencesKey] = encodeToString(coinsEntity) }
    }

    @OptIn(InternalSerializationApi::class)
    private fun decodeFromString(string: String): List<CoinsEntity> =
        try {
            Json.decodeFromString(ListSerializer(CoinsEntity::class.serializer()), string)
        } catch (e: Exception) {
            listOf()
        }

    private fun mapProductFromPrefs(prefs: Preferences): List<CoinsEntity> =
        prefs[productPreferencesKey]
            ?.takeIf(String::isNotEmpty)
            ?.let(this::decodeFromString) ?: listOf()

    private val productPreferencesKey = stringPreferencesKey(KEY)

    @OptIn(InternalSerializationApi::class)
    private fun encodeToString(products: List<CoinsEntity>): String =
        Json.encodeToString(
            ListSerializer(CoinsEntity::class.serializer()),
            products,
        )

    private companion object {
        const val KEY = "key"
    }
}