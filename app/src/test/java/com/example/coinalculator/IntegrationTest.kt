package com.example.coinalculator

import com.example.coinalculator.ui.coins.domain.ChangeFavoriteStateUseCase
import com.example.coinalculator.ui.coins.domain.Coin
import com.example.coinalculator.ui.coins.domain.CoinsRepository
import com.example.coinalculator.ui.coins.domain.ConsumeCoinCardUseCase
import com.example.coinalculator.ui.coins.presently.card.CoinCardViewModel
import com.example.coinalculator.ui.coins.presently.card.states.CoinCardScreenStates
import com.example.coinalculator.ui.coins.presently.card.states.CoinDetailsStatesMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import java.util.Random

const val ID = "3"

@RunWith(MockitoJUnitRunner::class)
class IntegrationTest {
    private lateinit var sut: CoinCardViewModel

    @Mock
    lateinit var coinsRepository: CoinsRepository


    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(StandardTestDispatcher())

    private val ioDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        val coinDetailsStatesMapper = CoinDetailsStatesMapper()

        val consumeCoinCard = ConsumeCoinCardUseCase(
            coinsRepository = coinsRepository
        )

        val addFavoriteUseCase = ChangeFavoriteStateUseCase(
            coinsRepository = coinsRepository,
        )
        sut = CoinCardViewModel(
            consumeCoinCardUseCase = consumeCoinCard,
            coinDetailsStatesMapper = coinDetailsStatesMapper,
            addFavoriteUseCase = addFavoriteUseCase,
            productId = ID
        )
    }

    @Test
    fun `changeFavoriteState EXPECTED  isFavorite = true`() = runTest {
        //arrange
        whenever(coinsRepository.consumeCoins()).thenReturn(fillingRepository())

        //act
        sut.loadCoinCard()
        sut.changeFavoriteState()

        assertEquals(true, sut.state.value.coin.isFavorite)
    }


    private fun fillingRepository() : Flow<List<Coin>> {
        val state = MutableStateFlow<List<Coin>>(listOf())

        for( i in 1..5) {
            state.value +=  createCoin(i)
        }
        return state
    }

    private fun createCoin( id: Int): Coin {
        return Coin(
            name = "Coin$id",
            image = "",
            price = Random().nextInt(1000).toString(),
            pricePercentageChange24h = 0f,
            priceChange24h = 0f,
            marketCap = 0,
            marketCapRank = 0,
            totalVolume = 0,
            isFavorite = false
        )
    }
}