package com.example.coinalculator

import com.example.coinalculator.ui.coins.domain.ChangeFavoriteStateUseCase
import com.example.coinalculator.ui.coins.domain.Coin
import com.example.coinalculator.ui.coins.domain.ConsumeCoinCardUseCase
import com.example.coinalculator.ui.coins.presently.card.CoinCardViewModel
import com.example.coinalculator.ui.coins.presently.card.states.CoinDetailsStatesMapper
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

    private val productId: String = "3"

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        sut = CoinCardViewModel (
            consumeCoinCardUseCase = ConsumeCoinCardUseCase,
            coinDetailsStatesMapper = coinDetailsStatesMapper,
            addFavoriteUseCase = addFavoriteUseCase,
            productId = productId
        )
    }

    @Test
    fun `requestCoinCard WHEN starting loading EXPECT isLoading flag in state`(){
        //arrange
        whenever(ConsumeCoinCardUseCase.invoke("3")).thenReturn(flowOf())

        //act
        sut.loadCoinCard()

        //assert
        Assert.assertTrue(sut.state.value.isLoading)
    }

    @Test
    fun `requestCoinCard WHEN product is loaded EXPECT reset isLoading flag and set product state`(){
        //arrange
        whenever(ConsumeCoinCardUseCase.invoke("3")).thenReturn(flowOf(loadCoin()))

        //act
        sut.loadCoinCard()

        //assert
        Assert.assertFalse(sut.state.value.isLoading)

    }

    @Test
    fun `requestCoinCard WHEN product loading has error EXPECT state has en error`() {
        // arrange
        whenever(ConsumeCoinCardUseCase.invoke("2")).thenReturn(flow { throw IllegalStateException() })

        // act
        sut.loadCoinCard()

        // assert
        Assert.assertTrue(sut.state.value.hasError)
    }


    private fun loadCoin() : Coin {
        return Coin (
            id = 3,
            name = "BTC",
            image  = "",
            price  = "60000",
            change24h  = 0f,
            isFavorite = false
        )
    }
}