package io.polarian.exchange

import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.BigInteger


data class SatoshiCoin(var name: String, var available: BigInteger, var limit: BigInteger)

data class SatoshiBalance(val price: BigInteger, val amount: BigInteger)

fun BigInteger.satoshiPrecision(): BigInteger {
    return this.divide(BigInteger.valueOf(100000000))
}

@Service
class SatoshiExchangeService {
    fun askOrder(baseCoin: SatoshiCoin, targetCoin: SatoshiCoin, balance: SatoshiBalance) {
        targetCoin.available -= balance.amount
        targetCoin.limit += balance.amount
    }

    fun bidOrder(baseCoin: SatoshiCoin, targetCoin: SatoshiCoin, balance: SatoshiBalance) {
        baseCoin.available -= (balance.price * balance.amount).satoshiPrecision().plus(BigInteger.ONE)
        baseCoin.limit += (balance.price * balance.amount).satoshiPrecision().plus(BigInteger.ONE)
    }

    fun askTrade(baseCoin: SatoshiCoin, targetCoin: SatoshiCoin, balance: SatoshiBalance, feeRate: BigDecimal) {
        val fee = (balance.price * balance.amount).satoshiPrecision().toBigDecimal().multiply(feeRate).setScale(0, BigDecimal.ROUND_UP).toBigInteger()

        baseCoin.available += (balance.price * balance.amount).satoshiPrecision() - fee
        targetCoin.limit -= balance.amount
    }

    fun bidTrade(baseCoin: SatoshiCoin, targetCoin: SatoshiCoin, balance: SatoshiBalance, feeRate: BigDecimal) {
        val fee = balance.amount.toBigDecimal().multiply(feeRate).setScale(0, BigDecimal.ROUND_UP).toBigInteger()

        targetCoin.available += balance.amount - fee
        baseCoin.limit -= (balance.price * balance.amount).satoshiPrecision().plus(BigInteger.ONE)
    }

    fun askCancel(baseCoin: SatoshiCoin, targetCoin: SatoshiCoin, balance: SatoshiBalance) {
        targetCoin.limit -= balance.amount
        targetCoin.available += balance.amount
    }

    fun bidCancel(baseCoin: SatoshiCoin, targetCoin: SatoshiCoin, balance: SatoshiBalance) {
        baseCoin.limit -= (balance.price * balance.amount).satoshiPrecision().plus(BigInteger.ONE)
        baseCoin.available += (balance.price * balance.amount).satoshiPrecision().plus(BigInteger.ONE)
    }
}