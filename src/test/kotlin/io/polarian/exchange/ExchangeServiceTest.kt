package io.polarian.exchange

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnitRunner
import java.math.BigDecimal

@RunWith(MockitoJUnitRunner::class)
class ExchangeServiceTest {

    @InjectMocks
    private lateinit var exchangeService: ExchangeService

    @Test
    fun test_bidOrder_bidTrade() {
        val btc = Coin("BTC", BigDecimal.valueOf(1000), BigDecimal.valueOf(0))
        val eth = Coin("ETH", BigDecimal.valueOf(0), BigDecimal.valueOf(0))
        val balance = Balance(BigDecimal.valueOf(0.000018), BigDecimal.valueOf(4.481))

        exchangeService.bidOrder(btc, eth, balance)

        assertThat(btc.available).isEqualByComparingTo(BigDecimal.valueOf(999.999919342))
        assertThat(btc.limit).isEqualByComparingTo(BigDecimal.valueOf(0.000080658))
        assertThat(eth.available).isEqualByComparingTo(BigDecimal.valueOf(0))
        assertThat(eth.limit).isEqualByComparingTo(BigDecimal.valueOf(0))

        exchangeService.bidTrade(btc, eth, balance)

        assertThat(btc.available).isEqualByComparingTo(BigDecimal.valueOf(999.999919342))
        assertThat(btc.limit).isEqualByComparingTo(BigDecimal.valueOf(0))
        assertThat(eth.available).isEqualByComparingTo(BigDecimal.valueOf(4.481))
        assertThat(eth.limit).isEqualByComparingTo(BigDecimal.valueOf(0))
    }

    @Test
    fun test_askOrder_askTrade() {
        val btc = Coin("BTC", BigDecimal.valueOf(0), BigDecimal.valueOf(0))
        val eth = Coin("ETH", BigDecimal.valueOf(1000), BigDecimal.valueOf(0))
        val balance = Balance(BigDecimal.valueOf(0.000018), BigDecimal.valueOf(2.187))

        exchangeService.askOrder(btc, eth, balance)

        assertThat(btc.available).isEqualByComparingTo(BigDecimal.valueOf(0))
        assertThat(btc.limit).isEqualByComparingTo(BigDecimal.valueOf(0))
        assertThat(eth.available).isEqualByComparingTo(BigDecimal.valueOf(997.813))
        assertThat(eth.limit).isEqualByComparingTo(BigDecimal.valueOf(2.187))

        exchangeService.askTrade(btc, eth, balance)

        assertThat(btc.available).isEqualByComparingTo(BigDecimal.valueOf(0.000039366))
        assertThat(btc.limit).isEqualByComparingTo(BigDecimal.valueOf(0))
        assertThat(eth.available).isEqualByComparingTo(BigDecimal.valueOf(997.813))
        assertThat(eth.limit).isEqualByComparingTo(BigDecimal.valueOf(0))
    }

    @Test
    fun test_bidOrder_bidCancel() {
        val btc = Coin("BTC", BigDecimal.valueOf(1000), BigDecimal.valueOf(0))
        val eth = Coin("ETH", BigDecimal.valueOf(0), BigDecimal.valueOf(0))
        val balance = Balance(BigDecimal.valueOf(0.000018), BigDecimal.valueOf(4.481))

        exchangeService.bidOrder(btc, eth, balance)

        assertThat(btc.available).isEqualByComparingTo(BigDecimal.valueOf(999.999919342))
        assertThat(btc.limit).isEqualByComparingTo(BigDecimal.valueOf(0.000080658))
        assertThat(eth.available).isEqualByComparingTo(BigDecimal.valueOf(0))
        assertThat(eth.limit).isEqualByComparingTo(BigDecimal.valueOf(0))

        exchangeService.bidCancel(btc, eth, balance)

        assertThat(btc.available).isEqualByComparingTo(BigDecimal.valueOf(1000))
        assertThat(btc.limit).isEqualByComparingTo(BigDecimal.valueOf(0))
        assertThat(eth.available).isEqualByComparingTo(BigDecimal.valueOf(0))
        assertThat(eth.limit).isEqualByComparingTo(BigDecimal.valueOf(0))
    }

    @Test
    fun test_askOrder_askCancel() {
        val btc = Coin("BTC", BigDecimal.valueOf(0), BigDecimal.valueOf(0))
        val eth = Coin("ETH", BigDecimal.valueOf(1000), BigDecimal.valueOf(0))
        val balance = Balance(BigDecimal.valueOf(0.000018), BigDecimal.valueOf(2.187))

        exchangeService.askOrder(btc, eth, balance)

        assertThat(btc.available).isEqualByComparingTo(BigDecimal.valueOf(0))
        assertThat(btc.limit).isEqualByComparingTo(BigDecimal.valueOf(0))
        assertThat(eth.available).isEqualByComparingTo(BigDecimal.valueOf(997.813))
        assertThat(eth.limit).isEqualByComparingTo(BigDecimal.valueOf(2.187))

        exchangeService.askCancel(btc, eth, balance)

        assertThat(btc.available).isEqualByComparingTo(BigDecimal.valueOf(0))
        assertThat(btc.limit).isEqualByComparingTo(BigDecimal.valueOf(0))
        assertThat(eth.available).isEqualByComparingTo(BigDecimal.valueOf(1000))
        assertThat(eth.limit).isEqualByComparingTo(BigDecimal.valueOf(0))
    }

}
