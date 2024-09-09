package com.example.coinalculator.ui.coins.data

import com.example.coinalculator.ui.coins.domain.CoinEntity
import com.example.coinalculator.ui.coins.domain.CoinsRepository
import com.example.coinalculator.ui.common.data.CommonRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CoinsRepositoryImpl(
    private val coinsMapper: Mapper,
    private val coinsRepository: CommonRepositoryImpl
) : CoinsRepository {
    override fun consumeCoins(): Flow<List<CoinEntity>> {
        return coinsRepository.getList()
            .map { coins ->
                coins.map(coinsMapper::fromEntity)
            }
    }

    override suspend fun changeFavoriteState(coin: CoinEntity) {
        val value = coinsMapper.toEntity(coin)
        coinsRepository.changeFavorite(value)
    }
}