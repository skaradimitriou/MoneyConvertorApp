package com.stathis.currencyconverter

object CurrencyConverter {

    /*

    BASE currency is USD
    EUR to CAD

    USD to EUR = 0.92 (prize for today)
    USD to CAD = 1.37

    (1.37 / 0.92) * 10 = 14.89

    (So we don't make a new api call in every change)
     */

    fun convert(
        fromCurrencyRateVsBaseCurrencyRate: Double,
        toCurrencyVsBaseCurrencyRate: Double,
        amount: Double
    ): Double {
        return (toCurrencyVsBaseCurrencyRate / fromCurrencyRateVsBaseCurrencyRate) * amount
    }
}