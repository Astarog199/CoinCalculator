package com.example.coinalculator.ui.calculator

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.coinalculator.ServiceLocator
import com.example.coinalculator.ui.calculator.data.CalculatorRemoteDataSource
import com.example.coinalculator.ui.calculator.data.CalculatorApiService
import com.example.coinalculator.ui.calculator.data.models.DataMapper
import com.example.coinalculator.ui.calculator.data.CalculatorDomainMapper
import com.example.coinalculator.ui.calculator.data.CalculatorLocalDataSource
import com.example.coinalculator.ui.calculator.data.CalculatorRepositoryImpl
import com.example.coinalculator.ui.calculator.domain.ConsumeCoinsUseCase
import com.example.coinalculator.ui.calculator.presently.ViewModelFactory
import com.example.coinalculator.ui.calculator.presently.states.CalcStateMapper
import com.example.coinalculator.ui.common.data.CommonDataMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


import retrofit2.converter.moshi.MoshiConverterFactory


object ServiceLocator {

    private var retrofitSingleton: Retrofit? = null
    private var calculatorRepositorySinlgleton: CalculatorRepositoryImpl? = null

    lateinit var applicationContext: Context

    fun provideGetCoinsUseCase() : ConsumeCoinsUseCase {
        return ConsumeCoinsUseCase(repository = provideRepository())
    }

    fun provideRepository() : CalculatorRepositoryImpl{
        val local = calculatorRepositorySinlgleton
        return local ?: run {
            val newRepository = CalculatorRepositoryImpl(
                repository = provideRemoteDataSource(),
                coinsRemoteDataSource = ServiceLocator.provideCoinsListRemoteDataSource(),
                calculatorLocalDataSource = provideCalculatorLocalDataSource(),
                dataMapper = DataMapper(),
                calculatorDomainMapper = CalculatorDomainMapper(),
                coroutineDispatcher = provideIOCoroutineDispatcher()
            )
            calculatorRepositorySinlgleton = newRepository
            newRepository
        }
    }
    
    fun provideViewModel() : ViewModelProvider.Factory{
        return ViewModelFactory(
            consumeCoinsUseCase = provideGetCoinsUseCase(),
            calcStateMapper = CalcStateMapper()
        )
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }
    
    private fun provideRetrofit(): Retrofit {
        val local = retrofitSingleton

        return local ?: run {
            val newRetrofit =Retrofit.Builder()
                .client(provideOkHttpClient())
                .baseUrl("https://api.coingecko.com/api/v3/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            retrofitSingleton = newRetrofit
            newRetrofit
        }
    }

    private fun provideDataApiService(): CalculatorApiService {
        return provideRetrofit().create(CalculatorApiService::class.java)
    }
    
    private fun provideRemoteDataSource(): CalculatorRemoteDataSource{
        return CalculatorRemoteDataSource(listCoinApi = provideDataApiService())
    }

    private fun provideIOCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    private fun provideCalculatorLocalDataSource(): CalculatorLocalDataSource{
        return CalculatorLocalDataSource(dataStore = applicationContext.appDataStore)
    }

    private val Context.appDataStore: DataStore<Preferences> by preferencesDataStore(name = "start_app")
}


