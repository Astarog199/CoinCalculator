package com.example.coinalculator.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.coinalculator.App
import com.example.coinalculator.ui.calculator.data.CalculatorApiService
import com.example.coinalculator.ui.calculator.data.CalculatorLocalDataSource
import com.example.coinalculator.ui.common.data.CommonApiService
import com.example.coinalculator.ui.common.data.room.CoinDao
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit

@Module
object DataModule {
    @Provides
    fun provideIOCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    fun provideCalculatorApiService(retrofit: Retrofit) : CalculatorApiService {
        return retrofit.create(CalculatorApiService::class.java)
    }

    @Provides
    fun provideDataApiService(retrofit: Retrofit): CommonApiService {
        return retrofit.create(CommonApiService::class.java)
    }

    @Provides
    fun provideRoom(context: Context): CoinDao {
        return (context as App).db.coinDao()
    }

    @Provides
    fun provideCalculatorLocalDataSource(context: Context): CalculatorLocalDataSource {
        return CalculatorLocalDataSource(
            context.appDataStore
        )
    }


    private val Context.appDataStore: DataStore<Preferences> by preferencesDataStore(name = "start_app")
}