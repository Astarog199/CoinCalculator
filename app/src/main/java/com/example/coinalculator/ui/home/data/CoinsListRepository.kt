package com.example.coinalculator.ui.home.data

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.await



class CoinsListRepository (private val listCoinApi: ListCoinApi){
    suspend fun getCoins(): Observable<List<String>> {
        return Observable.just(listCoinApi.getList().await())
    }
}