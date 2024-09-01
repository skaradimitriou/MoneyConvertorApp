package com.stathis.data.repository

import com.stathis.model.currency_convertor.ExchangeRates
import com.stathis.network.model.response.toExternalModel
import com.stathis.network.service.CurrencyService
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val currencyService: CurrencyService
) : CurrencyRepository {

    override suspend fun getExchangeRates(): ExchangeRates {
        return currencyService.getExchangeRates().toExternalModel(baseCurrency = "USD")
    }
}