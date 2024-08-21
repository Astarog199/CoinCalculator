package com.example.coinalculator.ui.calculator

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.coinalculator.ui.calculator.data.RemoteDataSource
import com.example.coinalculator.ui.calculator.data.CalculatorApiService
import com.example.coinalculator.ui.calculator.data.CalculatorDataMapper
import com.example.coinalculator.ui.calculator.data.CalculatorDomainMapper
import com.example.coinalculator.ui.calculator.data.CalculatorLocalDataSource
import com.example.coinalculator.ui.calculator.data.RepositoryImpl
import com.example.coinalculator.ui.calculator.domain.ConsumeCoinsUseCase
import com.example.coinalculator.ui.calculator.presently.ViewModelFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


import retrofit2.converter.moshi.MoshiConverterFactory


object ServiceLocator {

    private var calculatorRepositorySinlgleton: RepositoryImpl? = null

    lateinit var applicationContext: Context

    fun provideGetCoinsUseCase() : ConsumeCoinsUseCase {
        return ConsumeCoinsUseCase(repository = provideRepository())
    }

    fun provideRepository() : RepositoryImpl{
        val local = calculatorRepositorySinlgleton
        return local ?: run {
            val newRepository = RepositoryImpl(
                repository = provideRemoteDataSource(),
                calculatorLocalDataSource = provideCalculatorLocalDataSource(),
                calculatorDataMapper = CalculatorDataMapper(),
                calculatorDomainMapper = CalculatorDomainMapper(),
                coroutineDispatcher = provideIOCoroutineDispatcher()
            )
            calculatorRepositorySinlgleton = newRepository
            newRepository
        }
    }
    
    fun provideViewModel() : ViewModelProvider.Factory{
        return ViewModelFactory(
            consumeCoinsUseCase = provideGetCoinsUseCase()
        )
    }
    
    private fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(
                OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
                    it.level = HttpLoggingInterceptor.Level.BODY
                }).build())
            .baseUrl("https://api.coingecko.com/api/v3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private fun provideDataApiService(): CalculatorApiService {
        return provideRetrofit().create(CalculatorApiService::class.java)
    }
    
    private fun provideRemoteDataSource(): RemoteDataSource{
        return RemoteDataSource(listCoinApi = provideDataApiService())
    }

    private fun provideIOCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    private fun provideCalculatorLocalDataSource(): CalculatorLocalDataSource{
        return CalculatorLocalDataSource(dataStore = applicationContext.appDataStore)
    }

    private val Context.appDataStore: DataStore<Preferences> by preferencesDataStore(name = "start_app")
}


