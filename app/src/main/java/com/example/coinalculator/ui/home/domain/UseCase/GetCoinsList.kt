package com.example.coinalculator.ui.home.domain.UseCase


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetCoinsList {

//    fun get(){
//        RetrofitInstance.cryptocurrenciesApi.getList()
//            .enqueue(object : Callback<List<String>> {
//                override fun onResponse(
//                    call: Call<List<String>>,
//                    response: Response<List<String>>
//                ) {
//                    if (response.isSuccessful) {
//                        val result = response.body() ?: return
//                        var str = ""
//                        for (i in result) {
//                            str = str + "$i\n"
//                        }
////                        binding.textHome.text = str
//                    }
//                }
//
//                override fun onFailure(call: Call<List<String>>, t: Throwable) {
//                    Log.e("Network", "Something went wrong", t)
//                }
//            })
//    }
}