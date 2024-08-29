package com.stathis.network.model.response

import kotlinx.serialization.Serializable

@Serializable
open class CurrencyConvertorResponse(
    val message: String? = null
)