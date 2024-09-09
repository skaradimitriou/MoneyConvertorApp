package com.stathis.database.di

import android.content.Context
import androidx.room.Room
import com.stathis.database.db.CurrencyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCurrencyDb(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        CurrencyDatabase::class.java,
        CurrencyDatabase.NAME
    )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(db: CurrencyDatabase) = db.dao()
}