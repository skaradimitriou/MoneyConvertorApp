package com.stathis.data.di

import com.stathis.data.worker.SyncManager
import com.stathis.data.worker.WorkManagerSyncManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class WorkManagerModule {

    @Binds
    abstract fun bindSyncManager(impl: WorkManagerSyncManager): SyncManager
}