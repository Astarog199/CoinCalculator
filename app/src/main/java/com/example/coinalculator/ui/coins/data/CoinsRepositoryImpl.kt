package com.example.coinalculator.ui.coins.data

import com.example.coinalculator.ui.coins.domain.Coin
import com.example.coinalculator.ui.coins.domain.CoinsRepository
import com.example.coinalculator.ui.common.data.CommonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CoinsRepositoryImpl(
    private val coinsMapper: Mapper,
    private val coinsRepository: CommonRepository
) : CoinsRepository {
    override fun consumeCoins(): Flow<List<Coin>> {
        return coinsRepository.saveList()
            .map { coins ->
                coins.map(coinsMapper::fromEntity)
            }
    }

    override suspend fun changeFavoriteState(coin: Coin) {
        val value = coinsMapper.toEntity(coin)
        coinsRepository.addFavorite(value)
    }
}