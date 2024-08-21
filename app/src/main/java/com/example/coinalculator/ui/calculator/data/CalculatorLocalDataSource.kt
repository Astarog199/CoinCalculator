package com.example.coinalculator.ui.calculator.data

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

class CalculatorLocalDataSource(
    private val dataStore: DataStore<Preferences>,
) {

    fun consumeCoins() : Flow<List<CalculatorEntity>> = dataStore.data
        .map(::mapProductFromPrefs)

    suspend fun saveCoin(coins: CalculatorEntity) {
        dataStore.edit { prefs -> prefs[productPreferencesKey] = encodeToString(coins) }
    }

    @OptIn(InternalSerializationApi::class)
    private fun decodeFromString(string: String): List<CalculatorEntity> =
        try {
            Json.decodeFromString(ListSerializer(CalculatorEntity::class.serializer()), string)
        } catch (e: Exception) {
            listOf()
        }

    private fun mapProductFromPrefs(prefs: Preferences): List<CalculatorEntity> =
        prefs[productPreferencesKey]
            ?.takeIf(String::isNotEmpty)
            ?.let(this::decodeFromString) ?: listOf()

    private val productPreferencesKey = stringPreferencesKey(PRODUCT_KEY)

    @OptIn(InternalSerializationApi::class)
    private fun encodeToString(products: CalculatorEntity): String =
        Json.encodeToString(
            ListSerializer(CalculatorEntity::class.serializer()),
            listOf(products),
        )

    private companion object {
        const val PRODUCT_KEY = "layered_product_key"
    }
}