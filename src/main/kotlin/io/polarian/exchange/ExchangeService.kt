package io.polarian.exchange

import org.springframework.stereotype.Service
import java.math.BigDecimal


data class Coin(var name:String, var available:BigDecimal, var limit:BigDecimal)

data class Balance(val price:BigDecimal, val amount:BigDecimal)

@Service
class ExchangeService {
    fun askOrder(baseCoin:Coin, targetCoin:Coin, balance:Balance) {
        targetCoin.available -= balance.amount
        targetCoin.limit += balance.amount
    }

    fun bidOrder(baseCoin:Coin, targetCoin:Coin, balance:Balance) {
        baseCoin.available -= balance.price * balance.amount
        baseCoin.limit += balance.price * balance.amount
    }

    fun askTrade(baseCoin:Coin, targetCoin:Coin, balance:Balance) {
        baseCoin.available += balance.price * balance.amount
        targetCoin.limit -= balance.amount
    }

    fun bidTrade(baseCoin:Coin, targetCoin:Coin, balance:Balance) {
        targetCoin.available += balance.amount
        baseCoin.limit -= balance.price * balance.amount
    }

    fun askCancel(baseCoin:Coin, targetCoin:Coin, balance:Balance) {
        targetCoin.limit -= balance.amount
        targetCoin.available += balance.amount
    }

    fun bidCancel(baseCoin:Coin, targetCoin:Coin, balance:Balance) {
        baseCoin.limit -= balance.price * balance.amount
        baseCoin.available += balance.price * balance.amount
    }
}