package com.stathis.network.service

import com.stathis.network.model.response.ExchangeRatesResponse
import retrofit2.http.GET


interface CurrencyService {

    @GET("latest")
    suspend fun getExchangeRates(): ExchangeRatesResponse
}