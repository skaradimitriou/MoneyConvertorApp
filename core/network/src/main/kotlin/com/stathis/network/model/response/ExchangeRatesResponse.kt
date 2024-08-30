package com.stathis.network.model.response

import com.stathis.model.com.currency_convertor.CurrencyInfo
import com.stathis.model.com.currency_convertor.ExchangeRates
import com.stathis.model.com.currency_convertor.Meta
import kotlinx.serialization.Serializable

@Serializable
data class ExchangeRatesResponse(
    val meta: Meta,
    val data: Map<String, CurrencyInfo>
)

fun ExchangeRatesResponse.toExternalModel(
    baseCurrency : String
) = ExchangeRates(
    baseCurrency = baseCurrency,
    rates = data.mapValues { it.value.value },
    lastUpdatedDate = meta.lastUpdatedAt
)