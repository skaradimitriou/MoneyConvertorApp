package com.stathis.data.repository

import com.stathis.common.Syncable
import com.stathis.model.currency_convertor.ExchangeRates
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository : Syncable {

    suspend fun getExchangeRates(): Flow<ExchangeRates>
}