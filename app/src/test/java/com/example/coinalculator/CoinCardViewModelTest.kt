package com.example.coinalculator

import com.example.coinalculator.ui.coins.domain.AddFavoriteUseCase
import com.example.coinalculator.ui.coins.domain.CoinDetails
import com.example.coinalculator.ui.coins.domain.ConsumeCoinCard
import com.example.coinalculator.ui.coins.presently.card.CoinCardViewModel
import com.example.coinalculator.ui.coins.presently.card.states.CoinDetailsStatesMapper
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
        whenever(consumeCoinCard.invoke("3")).thenReturn(flowOf(loadCoin()))

        //act
        sut.loadCoinCard()

        //assert
        Assert.assertTrue(sut.state.value.isLoading)
    }

    private fun loadCoin() : CoinDetails {
        return CoinDetails (
            id = 0,
            name = "",
            market  = "",
            price  = "",
            change24h  = 0f,
            isFavorite = false
        )
    }

}