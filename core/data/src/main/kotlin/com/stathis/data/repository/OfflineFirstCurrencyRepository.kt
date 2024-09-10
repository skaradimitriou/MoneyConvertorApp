package com.stathis.data.repository

import com.stathis.common.Synchronizer
import com.stathis.data.mapper.toEntity
import com.stathis.database.db.CurrencyDatabase
import com.stathis.database.model.asExternalModel
import com.stathis.model.currency_convertor.ExchangeRates
import com.stathis.network.service.CurrencyService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstCurrencyRepository @Inject constructor(
    private val db: CurrencyDatabase, private val currencyService: CurrencyService
) : CurrencyRepository {

    private val dao = db.dao()

    override suspend fun syncWith(synchronizer: Synchronizer) {
        synchronizer.start(
            fetchRemoteData = { currencyService.getExchangeRates() },
            deleteLocalData = { dao.clearExchangeRates() },
            updateLocalData = { dao.upsertExchangeRates(it.toEntity("USD")) }
        )
    }

    override suspend fun getExchangeRates(): Flow<ExchangeRates> {
        return dao.getExchangeRates().map { it.asExternalModel() }
    }
}