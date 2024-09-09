package com.stathis.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.stathis.database.model.ExchangeRatesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM exchangerates")
    fun getExchangeRates(): Flow<ExchangeRatesEntity>

    @Upsert
    suspend fun upsertExchangeRates(currencies: ExchangeRatesEntity)

    @Query("DELETE FROM exchangerates")
    suspend fun clearExchangeRates()
}