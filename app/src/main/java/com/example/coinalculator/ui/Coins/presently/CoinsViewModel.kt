package com.example.coinalculator.ui.Coins.presently

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinalculator.ui.Coins.domain.Coin
import com.example.coinalculator.ui.Coins.domain.ConsumeCoinsUseCase
import com.example.coinalculator.ui.Coins.domain.FilterCoinsListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isError = MutableSharedFlow<Boolean>(extraBufferCapacity = 1)
    val isError: SharedFlow<Boolean> = _isError.asSharedFlow()

    private var _filter = MutableStateFlow<List<CoinVO>>(listOf())
    val filter: StateFlow<List<CoinVO>> = _filter.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        consumeCoin()
    }

    private fun consumeCoin() {
        _isLoading.value = true
        consumeCoinsUseCase()
            .map { coins ->
                coins.map(coinVOMapper::toCoinVO)
            }
            .onEach { coinVOs ->
                _isLoading.value = false
                _coin.value = coinVOs
            }
            .catch {
                _isLoading.value = false
                _isError.tryEmit(true)
            }
            .launchIn(viewModelScope)
    }


    fun searchCoin(arg: String) {
        scope.launch {
            _filter.value = filterCoinsListUseCase.searchCoin(_coin.value, arg)
        }
    }
}