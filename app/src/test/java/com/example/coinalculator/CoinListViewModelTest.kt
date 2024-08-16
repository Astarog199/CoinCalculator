package com.example.coinalculator

import com.example.coinalculator.ui.coins.domain.ConsumeCoinListUseCase
import com.example.coinalculator.ui.coins.domain.FilterCoinsListUseCase
import com.example.coinalculator.ui.coins.presently.list.CoinListViewModel
import com.example.coinalculator.ui.coins.presently.list.states.CoinStateMapper
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
    suspend fun `requestCoinList WHEN starting loading EXPECT isLoading flag in state`() {
        // arrange
        whenever(consumeCoinListUseCase.invoke()).thenReturn(flowOf())

        //act
        sut.loadCoins()

        //assert
        Assert.assertTrue(sut.coinState.value.isLoading)
    }
}