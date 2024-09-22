package com.example.coinalculator

import com.example.coinalculator.ui.favorite.domain.ConsumeFavoritesUseCase
import com.example.coinalculator.ui.favorite.presently.FavoriteViewModel
import com.example.coinalculator.ui.favorite.presently.states.FavoriteScreenStatesMapper
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest {
    private lateinit var sut: FavoriteViewModel

    @Mock
    lateinit var consumeFavoritesUseCase: ConsumeFavoritesUseCase

    @Mock
    lateinit var favoriteScreenStatesMapper: FavoriteScreenStatesMapper

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        sut = FavoriteViewModel(
            favoriteScreenStatesMapper = favoriteScreenStatesMapper,
            consumeFavoritesUseCase = consumeFavoritesUseCase
        )


    }

    @Test
    fun `requestCoinList WHEN starting loading EXPECT isLoading flag in state`() = runTest {
        whenever(consumeFavoritesUseCase.invoke()).thenReturn(flowOf())

        sut.loadCoins()

        Assert.assertTrue(sut.favoriteState.value.isLoading)
    }

    @Test
    fun `requestCoinList WHEN product is loaded EXPECT reset isLoading flag and set product state`() =
        runTest {

        }
}