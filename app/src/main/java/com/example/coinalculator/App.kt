package com.example.coinalculator

import android.app.Application
import androidx.room.Room
import com.example.coinalculator.ui.common.data.room.CoinsDB
import com.example.coinalculator.ServiceLocator as coinServiceLocator

class App: Application() {
    lateinit var db: CoinsDB

    override fun onCreate() {
        super.onCreate()
        coinServiceLocator.applicationContext = this

        db = Room.databaseBuilder(
            applicationContext,
            CoinsDB::class.java,
            "db"
        ).build()
    }
}