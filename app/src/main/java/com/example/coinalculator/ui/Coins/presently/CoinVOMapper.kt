package com.example.coinalculator.ui.Coins.presently

import com.example.coinalculator.ui.Coins.domain.Coin

class CoinVOMapper() {
    fun toCoinVO(coin: Coin) : CoinVO {
        return CoinVO(
            name = coin.name,
            market = coin.market,
            price = coin.price,
        )
    }


}