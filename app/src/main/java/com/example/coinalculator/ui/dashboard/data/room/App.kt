package com.example.coinalculator.ui.dashboard.data.room

import android.app.Application
import androidx.room.Room

class App: Application() {

    lateinit var db: CoinsDB

    override fun onCreate() {
        super.onCreate()
        db = Room.inMemoryDatabaseBuilder(
            this,
            CoinsDB::class.java,
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}