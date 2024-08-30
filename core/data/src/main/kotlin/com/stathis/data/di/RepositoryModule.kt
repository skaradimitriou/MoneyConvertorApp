package com.stathis.data.di

import com.stathis.data.repository.CurrencyRepository
import com.stathis.data.repository.CurrencyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCurrencyRepository(impl: CurrencyRepositoryImpl): CurrencyRepository
}