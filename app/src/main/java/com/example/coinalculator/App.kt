package com.example.coinalculator

import android.app.Application
import com.example.coinalculator.ServiceLocator as coinServiceLocator
import com.example.coinalculator.ui.calculator.ServiceLocator as calculatorRepository

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        coinServiceLocator.applicationContext = this
        calculatorRepository.applicationContext = this
    }
}