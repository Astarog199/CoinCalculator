package com.example.coinalculator.ui.CoinsList.presently

import com.example.coinalculator.ui.CoinsList.domain.Coin

class CoinVOMapper() {
    fun toCoinVO(coin: Coin) : CoinVO {
        return CoinVO(
            name = coin.name,
            market = coin.market,
            price = coin.price,
        )
    }


}