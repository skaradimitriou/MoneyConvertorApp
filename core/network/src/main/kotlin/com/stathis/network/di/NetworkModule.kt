package com.stathis.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.stathis.network.interceptor.HeadersInterceptor
import com.stathis.network.service.CurrencyService
import com.stathis.network.util.API_KEY
import com.stathis.network.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        return OkHttpClient
            .Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .addNetworkInterceptor(
                HeadersInterceptor(
                    headers = mapOf(
                        "apiKey" to API_KEY
                    )
                )
            ).build()
    }

    @Singleton
    @Provides
    fun provideCurrencyRetrofitService(
        client: OkHttpClient
    ) = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
        .build()
        .create(CurrencyService::class.java)
}