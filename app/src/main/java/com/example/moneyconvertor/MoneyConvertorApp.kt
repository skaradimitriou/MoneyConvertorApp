package com.example.moneyconvertor

import android.app.Application
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.stathis.data.worker.SyncWorker
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MoneyConvertorApp : Application() {

    override fun onCreate() {
        super.onCreate()

        WorkManager.getInstance(this).beginUniqueWork(
            SyncWorker.NAME,
            ExistingWorkPolicy.KEEP,
            SyncWorker.request
        ).enqueue()
    }
}