package com.example.coinalculator

import android.app.Application
import com.example.coinalculator.ui.coins.ServiceLocator as coinServiceLocator

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        coinServiceLocator.applicationContext = this
    }
}