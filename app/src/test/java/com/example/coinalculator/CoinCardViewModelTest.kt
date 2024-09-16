package com.example.coinalculator

import com.example.coinalculator.ui.coins.domain.ChangeFavoriteStateUseCase
import com.example.coinalculator.ui.coins.domain.CoinEntity
import com.example.coinalculator.ui.coins.domain.ConsumeCoinCardUseCase
import com.example.coinalculator.ui.coins.presently.card.CoinCardViewModel
import com.example.coinalculator.ui.coins.presently.card.states.CoinDetailsStatesMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class CoinCardViewModelTest {

    private lateinit var sut: CoinCardViewModel

    @Mock
    lateinit var ConsumeCoinCardUseCase: ConsumeCoinCardUseCase

    @Mock
    lateinit var coinDetailsStatesMapper: CoinDetailsStatesMapper

    @Mock
    lateinit var addFavoriteUseCase: ChangeFavoriteStateUseCase

    private val coinName: String = "Bitcoin"

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        sut = CoinCardViewModel (
            consumeCoinCardUseCase = ConsumeCoinCardUseCase,
            coinDetailsStatesMapper = coinDetailsStatesMapper,
            addFavoriteUseCase = addFavoriteUseCase,
            coinName = coinName
        )
    }

    @Test
    fun `requestCoinCard WHEN starting loading EXPECT isLoading flag in state`(){
        //arrange
        whenever(ConsumeCoinCardUseCase.invoke("Bitcoin")).thenReturn(flowOf())

        //act
        sut.loadCoinCard()

        //assert
        Assert.assertTrue(sut.state.value.isLoading)
    }

    @Test
    fun `requestCoinCard WHEN product is loaded EXPECT reset isLoading flag and set product state`(){
        //arrange
        whenever(ConsumeCoinCardUseCase.invoke("Bitcoin")).thenReturn(loadCoin())

        //act
        sut.loadCoinCard()

        //assert
        Assert.assertFalse(sut.state.value.isLoading)

    }

    @Test
    fun `requestCoinCard WHEN product loading has error EXPECT state has en error`() {
        // arrange
        whenever(ConsumeCoinCardUseCase.invoke("Bitcoin")).thenReturn(flow { throw IllegalStateException() })

        // act
        sut.loadCoinCard()

        // assert
        Assert.assertTrue(sut.state.value.hasError)
    }


    private fun loadCoin() : Flow<CoinEntity> {
        return flowOf( CoinEntity (
            name = "Bitcoin",
            image  = "https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png?1696501400",
            price  = 58888f,
            pricePercentageChange24h = -1.0778f,
            priceChange24h = -641.65076f,
            marketCap = 1163068419014,
            marketCapRank = 1,
            totalVolume = 41149675862,
            isFavorite = false
        ))
    }
}