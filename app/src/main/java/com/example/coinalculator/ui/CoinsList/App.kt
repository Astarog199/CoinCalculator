package com.example.coinalculator.ui.CoinsList

import android.app.Application

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

    init {
        instance = this
    }

    companion object {
        private var instance: App? = null
        fun getInstance(): App = instance!!
    }
}