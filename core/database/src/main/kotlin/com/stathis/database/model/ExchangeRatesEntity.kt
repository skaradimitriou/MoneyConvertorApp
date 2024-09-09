package com.stathis.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stathis.model.currency_convertor.CurrencyInfo

@Entity(tableName = "ExchangeRates")
data class ExchangeRatesEntity(

    @PrimaryKey
    val lastUpdatedDate: String,
    val baseCurrency: String,
    val rates: Map<String, CurrencyInfo>
)