package io.polarian.exchange

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnitRunner
import java.math.BigDecimal
import java.math.BigInteger

@RunWith(MockitoJUnitRunner::class)
class SatoshiExchangeServiceTest {

    @InjectMocks
    private lateinit var satoshiExchangeService: SatoshiExchangeService

    @Test
    fun test_bidOrder_bidTrade() {
        val btc = SatoshiCoin("BTC", BigInteger.valueOf(10000000), BigInteger.valueOf(0))
        val eth = SatoshiCoin("ETH", BigInteger.valueOf(0), BigInteger.valueOf(0))
        val balance = SatoshiBalance(BigInteger.valueOf(100001), BigInteger.valueOf(10000))
        val fee = BigDecimal.valueOf(0.01)

        satoshiExchangeService.bidOrder(btc, eth, balance)

        assertThat(btc.available).isEqualByComparingTo(BigInteger.valueOf(9999989))
        assertThat(btc.limit).isEqualByComparingTo(BigInteger.valueOf(11))
        assertThat(eth.available).isEqualByComparingTo(BigInteger.valueOf(0))
        assertThat(eth.limit).isEqualByComparingTo(BigInteger.valueOf(0))

        satoshiExchangeService.bidTrade(btc, eth, balance, fee)

        assertThat(btc.available).isEqualByComparingTo(BigInteger.valueOf(9999989))
        assertThat(btc.limit).isEqualByComparingTo(BigInteger.valueOf(0))
        assertThat(eth.available).isEqualByComparingTo(BigInteger.valueOf(9900))
        assertThat(eth.limit).isEqualByComparingTo(BigInteger.valueOf(0))
    }

    @Test
    fun test_askOrder_askTrade() {
        val btc = SatoshiCoin("BTC", BigInteger.valueOf(0), BigInteger.valueOf(0))
        val eth = SatoshiCoin("ETH", BigInteger.valueOf(10000000), BigInteger.valueOf(0))
        val balance = SatoshiBalance(BigInteger.valueOf(100001), BigInteger.valueOf(10000))
        val fee = BigDecimal.valueOf(0.01)

        satoshiExchangeService.askOrder(btc, eth, balance)

        assertThat(btc.available).isEqualByComparingTo(BigInteger.valueOf(0))
        assertThat(btc.limit).isEqualByComparingTo(BigInteger.valueOf(0))
        assertThat(eth.available).isEqualByComparingTo(BigInteger.valueOf(9990000))
        assertThat(eth.limit).isEqualByComparingTo(BigInteger.valueOf(10000))

        satoshiExchangeService.askTrade(btc, eth, balance, fee)

        assertThat(btc.available).isEqualByComparingTo(BigInteger.valueOf(9))
        assertThat(btc.limit).isEqualByComparingTo(BigInteger.valueOf(0))
        assertThat(eth.available).isEqualByComparingTo(BigInteger.valueOf(9990000))
        assertThat(eth.limit).isEqualByComparingTo(BigInteger.valueOf(0))
    }

    @Test
    fun test_bidOrder_bidCancel() {
        val btc = SatoshiCoin("BTC", BigInteger.valueOf(10000000), BigInteger.valueOf(0))
        val eth = SatoshiCoin("ETH", BigInteger.valueOf(0), BigInteger.valueOf(0))
        val balance = SatoshiBalance(BigInteger.valueOf(100001), BigInteger.valueOf(10000))

        assertThat(btc.available).isEqualByComparingTo(BigInteger.valueOf(10000000))
        assertThat(btc.limit).isEqualByComparingTo(BigInteger.valueOf(0))
        assertThat(eth.available).isEqualByComparingTo(BigInteger.valueOf(0))
        assertThat(eth.limit).isEqualByComparingTo(BigInteger.valueOf(0))

        satoshiExchangeService.bidOrder(btc, eth, balance)

        assertThat(btc.available).isEqualByComparingTo(BigInteger.valueOf(9999989))
        assertThat(btc.limit).isEqualByComparingTo(BigInteger.valueOf(11))
        assertThat(eth.available).isEqualByComparingTo(BigInteger.valueOf(0))
        assertThat(eth.limit).isEqualByComparingTo(BigInteger.valueOf(0))

        satoshiExchangeService.bidCancel(btc, eth, balance)

        assertThat(btc.available).isEqualByComparingTo(BigInteger.valueOf(10000000))
        assertThat(btc.limit).isEqualByComparingTo(BigInteger.valueOf(0))
        assertThat(eth.available).isEqualByComparingTo(BigInteger.valueOf(0))
        assertThat(eth.limit).isEqualByComparingTo(BigInteger.valueOf(0))
    }

    @Test
    fun test_askOrder_askCancel() {
        val btc = SatoshiCoin("BTC", BigInteger.valueOf(0), BigInteger.valueOf(0))
        val eth = SatoshiCoin("ETH", BigInteger.valueOf(10000000), BigInteger.valueOf(0))
        val balance = SatoshiBalance(BigInteger.valueOf(100001), BigInteger.valueOf(10000))

        assertThat(btc.available).isEqualByComparingTo(BigInteger.valueOf(0))
        assertThat(btc.limit).isEqualByComparingTo(BigInteger.valueOf(0))
        assertThat(eth.available).isEqualByComparingTo(BigInteger.valueOf(10000000))
        assertThat(eth.limit).isEqualByComparingTo(BigInteger.valueOf(0))

        satoshiExchangeService.askOrder(btc, eth, balance)

        assertThat(btc.available).isEqualByComparingTo(BigInteger.valueOf(0))
        assertThat(btc.limit).isEqualByComparingTo(BigInteger.valueOf(0))
        assertThat(eth.available).isEqualByComparingTo(BigInteger.valueOf(9990000))
        assertThat(eth.limit).isEqualByComparingTo(BigInteger.valueOf(10000))

        satoshiExchangeService.askCancel(btc, eth, balance)

        assertThat(btc.available).isEqualByComparingTo(BigInteger.valueOf(0))
        assertThat(btc.limit).isEqualByComparingTo(BigInteger.valueOf(0))
        assertThat(eth.available).isEqualByComparingTo(BigInteger.valueOf(10000000))
        assertThat(eth.limit).isEqualByComparingTo(BigInteger.valueOf(0))
    }
}
