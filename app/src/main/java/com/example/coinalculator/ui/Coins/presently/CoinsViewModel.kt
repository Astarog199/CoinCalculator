package com.example.coinalculator.ui.Coins.presently

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinalculator.ui.Coins.domain.Coin
import com.example.coinalculator.ui.Coins.domain.ConsumeCoinsUseCase
import com.example.coinalculator.ui.Coins.domain.FilterCoinsListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CoinsViewModel(
    private val consumeCoinsUseCase: ConsumeCoinsUseCase,
    private val filterCoinsListUseCase: FilterCoinsListUseCase,
    private val coinVOMapper: CoinVOMapper
) : ViewModel() {
    private var _coin = MutableStateFlow<List<CoinVO>>(listOf())
    val coin: StateFlow<List<CoinVO>> = _coin.asStateFlow()

    private var _filter = MutableStateFlow<List<CoinVO>>(listOf())
    val filter: StateFlow<List<CoinVO>> = _filter.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        consumeCoin()
    }

    private fun consumeCoin() {
        consumeCoinsUseCase()
            .map { coins ->
                coins.map(coinVOMapper::toCoinVO)
            }
            .onEach { coinVOs ->
                _coin.value = coinVOs
            }
            .catch {

            }
            .launchIn(viewModelScope)
    }


    fun searchCoin(arg: String) {
        scope.launch {
//            _filter.value = filterCoinsListUseCase.searchCoin(arg)
        }
    }
}
