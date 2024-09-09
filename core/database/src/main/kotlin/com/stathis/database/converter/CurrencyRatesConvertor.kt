package com.stathis.database.converter

import com.stathis.model.currency_convertor.CurrencyInfo

class CurrencyRatesConvertor {

    //example => "key:code,value/key:code,value

    fun fromCurrenciesMapToString(currencies: Map<String, CurrencyInfo>): String {
        var result = ""
        currencies.forEach {
            result += "${it.key}:${it.value.code}, ${it.value.value}/"
        }
        return result
    }

    fun fromCurrenciesStringToMap(string: String): Map<String, CurrencyInfo> {
        val result = mutableMapOf<String, CurrencyInfo>()
        val mapEntries = string.split("/")
        mapEntries.forEach {
            val keyValue = it.split(":")
            val key = keyValue.firstOrNull() ?: ""
            val value = keyValue.getOrNull(1)?.split(",")
            val currencyInfo = CurrencyInfo(
                code = value?.firstOrNull() ?: "",
                value = value?.lastOrNull()?.toDouble() ?: 0.0
            )

            result[key] = currencyInfo
        }

        return result
    }
}