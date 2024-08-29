package com.stathis.model.com.currency_convertor

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyInfo(
    val code: String,
    val value: Double
)
