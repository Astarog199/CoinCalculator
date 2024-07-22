package com.example.coinalculator.ui.Coins.presently

interface CoinListView {
    fun showProgress()
    fun hideProgress()
    fun hidePullToRefresh()
    fun showCoins(productList: List<CoinVO>)
    fun hideCoins()
    fun showError()
}