package com.example.coinalculator.ui.dashboard.presently

import com.example.coinalculator.ui.dashboard.domain.Coin

class CoinVOMapper() {
    fun toCoinVO(coin: Coin) : CoinVO {
        return CoinVO(
            name = coin.name,
            market = coin.market,
            price = coin.price,
        )
    }


}