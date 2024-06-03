package com.example.coinalculator.ui.home.data

import com.example.coinalculator.ui.home.data.API.retrofit
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.await



class CoinsListRepository {
    suspend fun getCoins(): Observable<List<String>> {
        return Observable.just(retrofit.getList().await())
    }
}