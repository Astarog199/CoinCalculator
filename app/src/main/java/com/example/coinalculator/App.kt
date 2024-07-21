package com.example.coinalculator

import android.app.Application
import com.example.coinalculator.ui.Coins.ServiceLocator as coinServiceLocator

class App: Application() {

//    lateinit var db: CoinsDB
//
//    override fun onCreate() {
//        super.onCreate()
//        db = Room.inMemoryDatabaseBuilder(
//            this,
//            CoinsDB::class.java,
//        )
//            .fallbackToDestructiveMigration()
//            .build()
//    }
//

    override fun onCreate() {
        super.onCreate()
        coinServiceLocator.applicationContext = this
    }
}