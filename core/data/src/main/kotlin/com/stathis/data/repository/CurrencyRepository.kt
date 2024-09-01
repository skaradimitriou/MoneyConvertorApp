package com.stathis.data.repository

import com.stathis.model.currency_convertor.ExchangeRates

interface CurrencyRepository {

    suspend fun getExchangeRates(): ExchangeRates
}