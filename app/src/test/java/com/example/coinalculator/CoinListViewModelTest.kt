package com.example.coinalculator

import com.example.coinalculator.ui.coins.domain.Coin
import com.example.coinalculator.ui.coins.domain.ConsumeCoinListUseCase
import com.example.coinalculator.ui.coins.domain.FilterCoinsListUseCase
import com.example.coinalculator.ui.coins.presently.list.CoinListViewModel
import com.example.coinalculator.ui.coins.presently.list.states.CoinState
import com.example.coinalculator.ui.coins.presently.list.states.CoinStateMapper
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.argThat
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class CoinListViewModelTest {

    private lateinit var sut: CoinListViewModel

    @Mock
    lateinit var consumeCoinListUseCase: ConsumeCoinListUseCase

    @Mock
    lateinit var coinStateMapper: CoinStateMapper

    @Mock
    lateinit var filterCoinsListUseCase: FilterCoinsListUseCase

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        sut = CoinListViewModel(
            coinStateMapper = coinStateMapper,
            consumeCoinsUseCase = consumeCoinListUseCase,
            filterCoinsListUseCase = filterCoinsListUseCase
        )
    }

    @Test
    fun `requestCoinList WHEN starting loading EXPECT isLoading flag in state`() = runTest {
        // arrange
        whenever(consumeCoinListUseCase.invoke()).thenReturn(flowOf())

        //act
        sut.loadCoins()

        //assert
        Assert.assertTrue(sut.coinState.value.isLoading)
    }

    @Test
    fun `requestCoinList WHEN product is loaded EXPECT reset isLoading flag and set product state`() =
        runTest {
            // arrange
            val expectedProducts = loadCoins()

            // act
            sut.loadCoins()

            // assert
            Assert.assertFalse(sut.coinState.value.isLoading)
            assertEquals(expectedProducts, sut.coinState.value.coinsList)
        }

    @Test
    fun `requestCoinList WHEN product loading has error EXPECT state has en error`() = runTest {
        // arrange
        whenever(consumeCoinListUseCase.invoke()).thenReturn(flow { throw IllegalStateException() })

        // act
        sut.loadCoins()

        // assert
        Assert.assertTrue(sut.coinState.value.hasError)
    }




    private fun loadCoins(): List<CoinState> {
        whenever(consumeCoinListUseCase.invoke())
            .thenReturn(flowOf(listOf(loadCoin(id = 1), loadCoin(id = 2))))

        val state1 = CoinState(id = 1)
        val state2 = CoinState(id = 2)

        whenever(coinStateMapper.toCoinState(argThat { id == 1 })).thenReturn(state1)
        whenever(coinStateMapper.toCoinState(argThat { id == 2 })).thenReturn(state2)

        return listOf(state1, state2)
    }

    private fun loadCoin(id: Int) : Coin {
        return Coin (
            id = id,
            name = "Coin$id",
            image  = "",
            price  = "60000",
            change24h  = 0f,
            isFavorite = false
        )
    }
}