package com.example.coinalculator

import com.example.coinalculator.ui.coins.domain.AddFavoriteUseCase
import com.example.coinalculator.ui.coins.domain.CoinDetails
import com.example.coinalculator.ui.coins.domain.ConsumeCoinCard
import com.example.coinalculator.ui.coins.presently.card.CoinCardViewModel
import com.example.coinalculator.ui.coins.presently.card.states.CoinCardStates
import com.example.coinalculator.ui.coins.presently.card.states.CoinDetailsStatesMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import org.mockito.stubbing.OngoingStubbing

@RunWith(MockitoJUnitRunner::class)
class CoinCardViewModelTest {

    private lateinit var sut: CoinCardViewModel

    @Mock
    lateinit var consumeCoinCard: ConsumeCoinCard

    @Mock
    lateinit var coinDetailsStatesMapper: CoinDetailsStatesMapper

    @Mock
    lateinit var addFavoriteUseCase: AddFavoriteUseCase

    private val productId: String = "3"

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        sut = CoinCardViewModel (
            consumeCoinCard = consumeCoinCard,
            coinDetailsStatesMapper = coinDetailsStatesMapper,
            addFavoriteUseCase = addFavoriteUseCase,
            productId = productId
        )
    }

    @Test
    fun `requestCoinCard WHEN starting loading EXPECT isLoading flag in state`(){
        //arrange
        whenever(consumeCoinCard.invoke("3")).thenReturn(flowOf())

        //act
        sut.loadCoinCard()

        //assert
        Assert.assertTrue(sut.state.value.isLoading)
    }

    @Test
    fun `requestCoinCard WHEN product is loaded EXPECT reset isLoading flag and set product state`(){
        //arrange
        whenever(consumeCoinCard.invoke("3")).thenReturn(flowOf(loadCoin()))

        //act
        sut.loadCoinCard()

        //assert
        Assert.assertFalse(sut.state.value.isLoading)

    }

    @Test
    fun `requestCoinCard WHEN product loading has error EXPECT state has en error`() {
        // arrange
        whenever(consumeCoinCard.invoke("2")).thenReturn(flow { throw IllegalStateException() })

        // act
        sut.loadCoinCard()

        // assert
        Assert.assertTrue(sut.state.value.hasError)
    }


    private fun loadCoin() : CoinDetails {
        return CoinDetails (
            id = 3,
            name = "BTC",
            market  = "Binance",
            price  = "60000",
            change24h  = 0f,
            isFavorite = false
        )
    }
}