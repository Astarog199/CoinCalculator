package com.example.coinalculator.ui.Coins.presently

import com.example.coinalculator.ui.Coins.domain.ConsumeCoinsUseCase
import com.example.coinalculator.ui.Coins.domain.FilterCoinsListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class PromoListPresenter(
    private val coinVOMapper: CoinVOMapper,
    private val consumeCoinsUseCase: ConsumeCoinsUseCase,
    private val filterCoinsListUseCase: FilterCoinsListUseCase
) {
    private lateinit var coroutineScope: CoroutineScope
    private var _view: CoinListView? = null
    private val view: CoinListView
        get() = _view!!

    fun onViewAttached(view: CoinListView) {
        _view = view
        coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    }

    fun loadCoins() {
        consumeCoinsUseCase()
            .map { promos ->
                promos.map(coinVOMapper::toCoinVO)
            }
            .onStart {
                view.showProgress()
                view.hideCoins()
            }
            .onEach { coinVOMapper ->
                view.showProgress()
                view.showCoins(coinVOMapper)
            }
            .catch {
                view.showError()
            }
            .launchIn(coroutineScope)
    }
    fun dispose(){
        coroutineScope.cancel()
    }

    fun refresh(){
        loadCoins()
    }

}