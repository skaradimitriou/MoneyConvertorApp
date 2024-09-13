package com.stathis.currencyconverter

import org.junit.Test

class CurrencyConvertorTest {

    @Test
    fun `convert value in USD to EUR`() {
        val usdVsUsdPrice = 1.0
        val eurVsUsdPrice = 0.92

        val result = CurrencyConverter.convert(
            fromCurrencyRateVsBaseCurrencyRate = usdVsUsdPrice,
            toCurrencyVsBaseCurrencyRate = eurVsUsdPrice,
            amount = 1.0
        )

        assert(result == 0.92)
    }

    @Test
    fun `convert value USD to USD`() {
        val usdVsUsdPrice = 1.0

        val result = CurrencyConverter.convert(
            fromCurrencyRateVsBaseCurrencyRate = usdVsUsdPrice,
            toCurrencyVsBaseCurrencyRate = usdVsUsdPrice,
            amount = 20.5
        )

        assert(result == 20.5)
    }

    @Test
    fun `convert zero amount `() {
        val result = CurrencyConverter.convert(
            fromCurrencyRateVsBaseCurrencyRate = 1.0,
            toCurrencyVsBaseCurrencyRate = 1.0,
            amount = 0.0
        )

        assert(result == 0.0)
    }
}