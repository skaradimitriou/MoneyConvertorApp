package com.stathis.testing.repository

import com.stathis.common.Synchronizer
import com.stathis.data.repository.CurrencyRepository
import com.stathis.model.currency_convertor.ExchangeRates
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class TestCurrencyRepository : CurrencyRepository {

    private val exchangeRates: MutableSharedFlow<ExchangeRates> = MutableSharedFlow(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override fun getExchangeRates(): Flow<ExchangeRates> {
        return exchangeRates
    }

    fun sendExchangeRates(exchangeRates: ExchangeRates) {
        this.exchangeRates.tryEmit(exchangeRates)
    }

    override suspend fun syncWith(synchronizer: Synchronizer) {}
}