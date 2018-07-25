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
    fun printResult(name:String, baseCoin:SatoshiCoin, targetCoin:SatoshiCoin, balanace:SatoshiBalance, feeRate:BigDecimal?, fee:BigInteger?) {
        println("-${name}-------------------------------------------------------------------------------")
        println(baseCoin)
        println(targetCoin)
        println(balanace)
        println(feeRate)
        println(fee)
    }

    fun askOrder(baseCoin: SatoshiCoin, targetCoin: SatoshiCoin, balance: SatoshiBalance) {
        targetCoin.available -= balance.amount
        targetCoin.limit += balance.amount

        printResult("askOrder", baseCoin, targetCoin, balance, null, null)
    }

    fun bidOrder(baseCoin: SatoshiCoin, targetCoin: SatoshiCoin, balance: SatoshiBalance) {
        baseCoin.available -= (balance.price * balance.amount).satoshiPrecision().plus(BigInteger.ONE)
        baseCoin.limit += (balance.price * balance.amount).satoshiPrecision().plus(BigInteger.ONE)

        printResult("bidOrder", baseCoin, targetCoin, balance, null, null)
    }

    fun askTrade(baseCoin: SatoshiCoin, targetCoin: SatoshiCoin, balance: SatoshiBalance, feeRate: BigDecimal) {
        val fee = (balance.price * balance.amount).satoshiPrecision().toBigDecimal().multiply(feeRate).setScale(0, BigDecimal.ROUND_UP).toBigInteger()

        baseCoin.available += (balance.price * balance.amount).satoshiPrecision() - fee
        targetCoin.limit -= balance.amount

        printResult("askTrade", baseCoin, targetCoin, balance, feeRate, fee)
    }

    fun bidTrade(baseCoin: SatoshiCoin, targetCoin: SatoshiCoin, balance: SatoshiBalance, feeRate: BigDecimal) {
        val fee = balance.amount.toBigDecimal().multiply(feeRate).setScale(0, BigDecimal.ROUND_UP).toBigInteger()

        targetCoin.available += balance.amount - fee
        baseCoin.limit -= (balance.price * balance.amount).satoshiPrecision().plus(BigInteger.ONE)

        printResult("bidTrade", baseCoin, targetCoin, balance, feeRate, fee)
    }

    fun askCancel(baseCoin: SatoshiCoin, targetCoin: SatoshiCoin, balance: SatoshiBalance) {
        targetCoin.limit -= balance.amount
        targetCoin.available += balance.amount

        printResult("askCancel", baseCoin, targetCoin, balance, null, null)
    }

    fun bidCancel(baseCoin: SatoshiCoin, targetCoin: SatoshiCoin, balance: SatoshiBalance) {
        baseCoin.limit -= (balance.price * balance.amount).satoshiPrecision().plus(BigInteger.ONE)
        baseCoin.available += (balance.price * balance.amount).satoshiPrecision().plus(BigInteger.ONE)

        printResult("bidCancel", baseCoin, targetCoin, balance, null, null)
    }
}