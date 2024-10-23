package com.example.coinalculator

import android.app.Application
import androidx.room.Room
import com.example.coinalculator.di.AppComponent
import com.example.coinalculator.di.DaggerAppComponent
import com.example.coinalculator.ui.common.data.room.CoinsDB
import com.google.firebase.crashlytics.BuildConfig
import com.google.firebase.crashlytics.FirebaseCrashlytics

class App: Application() {
    lateinit var db: CoinsDB
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
       appComponent = DaggerAppComponent.factory().create(this)

        db = Room.databaseBuilder(
            applicationContext,
            CoinsDB::class.java,
            "db"
        ).build()

        FirebaseCrashlytics.getInstance().isCrashlyticsCollectionEnabled = true// !BuildConfig.DEBUG
    }
}