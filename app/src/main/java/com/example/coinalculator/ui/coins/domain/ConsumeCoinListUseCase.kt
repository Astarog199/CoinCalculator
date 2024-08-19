package com.example.coinalculator.ui.coins.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ConsumeCoinListUseCase(
    private val coinsRepository: CoinsRepository
) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private lateinit var coins: Flow<List<Coin>>

    operator fun invoke(): Flow<List<Coin>> {
        return coinsRepository.consumeCoins()
    }
}