package com.example.coinalculator

import com.example.coinalculator.ui.coins.domain.ChangeFavoriteStateUseCase
import com.example.coinalculator.ui.coins.domain.Coin
import com.example.coinalculator.ui.coins.domain.CoinsRepository
import com.example.coinalculator.ui.coins.domain.ConsumeCoinCardUseCase
import com.example.coinalculator.ui.coins.presently.card.CoinCardViewModel
import com.example.coinalculator.ui.coins.presently.card.states.CoinCardScreenStates
import com.example.coinalculator.ui.coins.presently.card.states.CoinDetailsStatesMapper
import com.example.coinalculator.ui.coins.presently.list.states.CoinListState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import java.util.Random


class CoinsRepositoryTest() : CoinsRepository {

    val state = MutableStateFlow<List<Coin>>(listOf())

    override fun consumeCoins(): Flow<List<Coin>> = state.asStateFlow()

    override suspend fun changeFavoriteState(coin: Coin) {
        TODO("Not yet implemented")
    }

    override fun removeFavorite(id: String) {
        TODO("Not yet implemented")
    }

    fun add(newCoin: Coin){
        state.value += newCoin
    }
}

@RunWith(MockitoJUnitRunner::class)
class IntegrationTest {
    private lateinit var sut: CoinCardViewModel

    val  coinsRepository = CoinsRepositoryTest()

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
            ConsumeCoinCardUseCase = consumeCoinCard,
            coinDetailsStatesMapper = coinDetailsStatesMapper,
            addFavoriteUseCase = addFavoriteUseCase,
            productId = "3"
        )
    }

    @Test
    fun `requestCoins EXPECTED  show all states`() {
        //arrange
        fillingRepository()
        whenever(coinsRepository.consumeCoins())

        val expectedInitialState = CoinCardScreenStates()
        val expectedLoadingState = CoinCardScreenStates(isLoading = true)
        val expectedDataState = CoinCardScreenStates()

        //act
        sut.loadCoinCard()

        val result = mutableListOf<CoinListState>()

    }


    private fun fillingRepository() {
        for( i in 1..5) {
            coinsRepository.add(newCoin = createCoin(i))
        }
    }

    private fun createCoin( id: Int): Coin {
        return Coin(
            id = id,
            name = "Coin$id",
            market = "Binance",
            price = Random().nextInt(1000).toString(),
            change24h = 0f,
            isFavorite = false
        )
    }
}