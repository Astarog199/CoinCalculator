package com.example.coinalculator

import com.example.coinalculator.ui.coins.domain.CoinEntity
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
            .thenReturn(flowOf(listOf(loadCoin(name = "Bitcoin"), loadCoin(name = "ethereum"))))

        val state1 = CoinState(id = 1)
        val state2 = CoinState(id = 2)

        whenever(coinStateMapper.toCoinState(argThat { name == "Bitcoin" })).thenReturn(state1)
        whenever(coinStateMapper.toCoinState(argThat { name == "ethereum" })).thenReturn(state2)

        return listOf(state1, state2)
    }

    private fun loadCoin(name: String) : CoinEntity {
        return CoinEntity (
            name = name,
            image  = "https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png?1696501400",
            price  = "58888",
            pricePercentageChange24h = -1.0778f,
            priceChange24h = -641.65076f,
            marketCap = 1163068419014,
            marketCapRank = 1,
            totalVolume = 41149675862,
            isFavorite = false
        )
    }
}