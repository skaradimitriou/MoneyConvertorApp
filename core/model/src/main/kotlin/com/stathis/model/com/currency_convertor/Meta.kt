package com.stathis.model.com.currency_convertor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    @SerialName("last_updated_at")
    val lastUpdatedAt: String
)
