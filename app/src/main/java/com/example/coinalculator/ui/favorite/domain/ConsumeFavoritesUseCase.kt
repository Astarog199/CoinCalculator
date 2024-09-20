package com.example.coinalculator.ui.favorite.domain

import com.example.coinalculator.ui.common.data.CommonRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ConsumeFavoritesUseCase(
    private val coinsRepository: CommonRepositoryImpl
) {

    private val scope = CoroutineScope(Dispatchers.IO)
    private lateinit var coins: Flow<List<FavoriteEntity>>

    suspend operator fun invoke() = suspendCoroutine {
        scope.launch {
            coins = coinsRepository.consumeFavoriteCoins().map { coin ->
                coin.filter { v ->
                    v.isFavorite
                }
            }
            it.resume(coins)
        }
    }
}