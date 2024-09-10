package com.stathis.data.mapper

import com.stathis.database.model.ExchangeRatesEntity
import com.stathis.network.model.response.ExchangeRatesResponse

fun ExchangeRatesResponse.toEntity(baseCurrencyCode: String): ExchangeRatesEntity {
    return ExchangeRatesEntity(
        baseCurrency = meta.lastUpdatedAt,
        lastUpdatedDate = meta.lastUpdatedAt,
        rates = data
    )
}